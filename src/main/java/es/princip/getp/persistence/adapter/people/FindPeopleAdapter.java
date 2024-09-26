package es.princip.getp.persistence.adapter.people;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.application.like.people.port.out.CheckPeopleLikePort;
import es.princip.getp.application.like.people.port.out.CountPeopleLikePort;
import es.princip.getp.application.people.command.PeopleSearchFilter;
import es.princip.getp.application.people.command.PeopleSearchOrder;
import es.princip.getp.application.people.port.out.FindPeoplePort;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.persistence.adapter.like.people.QPeopleLikeJpaEntity;
import es.princip.getp.persistence.adapter.member.QMemberJpaEntity;
import es.princip.getp.persistence.adapter.people.mapper.PeopleQueryMapper;
import es.princip.getp.persistence.adapter.people.model.PeopleJpaEntity;
import es.princip.getp.persistence.adapter.people.model.PeopleProfileJpaVO;
import es.princip.getp.persistence.adapter.people.model.QPeopleJpaEntity;
import es.princip.getp.persistence.support.QueryDslSupport;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Repository
@RequiredArgsConstructor
// TODO: 조회 성능 개선 필요
class FindPeopleAdapter extends QueryDslSupport implements FindPeoplePort {

    private static final QPeopleJpaEntity people = QPeopleJpaEntity.peopleJpaEntity;
    private static final QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;
    private static final QPeopleLikeJpaEntity like = QPeopleLikeJpaEntity.peopleLikeJpaEntity;

    private final CheckPeopleLikePort checkPeopleLikePort;
    private final CountPeopleLikePort countPeopleLikePort;

    private final PeopleQueryMapper mapper;

    private Map<Long, Tuple> findMemberAndPeopleBy(final PeopleId... peopleIds) {
        final Long[] ids = Arrays.stream(peopleIds)
            .map(PeopleId::getValue)
            .toArray(Long[]::new);
        return queryFactory.select(
            member.nickname,
            member.profileImage,
            people.id
        )
        .from(people)
        .join(member)
        .on(people.memberId.eq(member.id))
        .where(people.id.in(ids)).fetch().stream()
        .collect(toMap(tuple -> tuple.get(people.id), Function.identity()));
    }

    private Optional<Tuple> findMemberAndPeopleBy(final PeopleId peopleId) {
        return Optional.ofNullable(
            queryFactory.select(
                member.nickname,
                member.profileImage,
                people.id
            )
            .from(people)
            .join(member).on(people.memberId.eq(member.id))
            .where(people.id.eq(peopleId.getValue()))
            .fetchOne()
        );
    }

    private BooleanExpression memberIdEq(final MemberId memberId) {
        return Optional.ofNullable(memberId)
            .map(MemberId::getValue)
            .map(member.id::eq)
            .orElse(null);
    }

    private <T> JPAQuery<T> buildQuery(
        final JPAQuery<T> selectFrom,
        final PeopleSearchFilter filter,
        final MemberId memberId
    ) {
        if (filter.isLiked()) {
            selectFrom.join(like).on(people.id.eq(like.peopleId))
                .join(member).on(like.memberId.eq(member.id))
                .where(memberIdEq(memberId));
        }
        if (!StringUtils.isAllBlank(filter.getKeyword())) {
            selectFrom.join(member).on(people.memberId.eq(member.id))
                .where(member.nickname.startsWith(filter.getKeyword()));
        }
        selectFrom.where(people.profile.isNotNull());
        return selectFrom;
    }

    private List<CardPeopleResponse> getCardPeopleContent(
        final Pageable pageable,
        final PeopleSearchFilter filter,
        final MemberId memberId
    ) {
        final List<PeopleJpaEntity> content = buildQuery(queryFactory.selectFrom(people), filter, memberId)
            .orderBy(getOrderSpecifiers(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        final PeopleId[] peopleIds = getPeopleIds(content);

        final Map<PeopleId, Long> likesCounts = countPeopleLikePort.countBy(peopleIds);
        final Map<Long, Tuple> memberAndPeople = findMemberAndPeopleBy(peopleIds);

        return content.stream().map(people -> {
            final Long peopleId = people.getId();
            return new CardPeopleResponse(
                peopleId,
                memberAndPeople.get(peopleId).get(member.nickname),
                memberAndPeople.get(peopleId).get(member.profileImage),
                0,
                likesCounts.get(new PeopleId(peopleId)),
                mapper.mapToCardPeopleProfileResponse(people.getProfile())
            );
        }).toList();
    }

    private static OrderSpecifier<?> getOrderSpecifier(final Sort.Order order, final PeopleSearchOrder peopleOrder) {
        final Order converted = convertTo(order);
        return switch (peopleOrder) {
            // TODO: case LIKES_COUNT
            case CREATED_AT -> new OrderSpecifier<>(converted, people.createdAt);
            default -> new OrderSpecifier<>(converted, people.id);
        };
    }

    private static OrderSpecifier<?>[] getOrderSpecifiers(final Sort sort) {
        return sort.stream()
            .map(order -> getOrderSpecifier(order, PeopleSearchOrder.get(order.getProperty())))
            .toArray(OrderSpecifier[]::new);
    }

    private static PeopleId[] getPeopleIds(final List<PeopleJpaEntity> peopleList) {
        return peopleList.stream()
            .map(PeopleJpaEntity::getId)
            .map(PeopleId::new)
            .toArray(PeopleId[]::new);
    }

    @Override
    public Page<CardPeopleResponse> findCardBy(
        final Pageable pageable,
        final PeopleSearchFilter filter,
        final MemberId memberId
    ) {
        return applyPagination(
            pageable,
            getCardPeopleContent(pageable, filter, memberId),
            countQuery -> buildQuery(countQuery.select(people.count()).from(people), filter, memberId)
        );
    }

    @Override
    public DetailPeopleResponse findDetailBy(final MemberId memberId, final PeopleId peopleId) {
        final PeopleProfileJpaVO profile = Optional.ofNullable(
                queryFactory.select(people)
                    .from(people)
                    .where(people.id.eq(peopleId.getValue())
                        .and(people.profile.isNotNull()))
                    .fetchOne()
            )
            .orElseThrow(NotFoundPeopleException::new)
            .getProfile();

        final Tuple memberAndPeople = findMemberAndPeopleBy(peopleId)
            .orElseThrow(NotFoundPeopleException::new);

        return new DetailPeopleResponse(
            peopleId.getValue(),
            memberAndPeople.get(member.nickname),
            memberAndPeople.get(member.profileImage),
            0,
            countPeopleLikePort.countBy(peopleId),
            checkPeopleLikePort.existsBy(memberId, peopleId),
            mapper.mapToDetailPeopleProfileResponse(profile)
        );
    }

    @Override
    public PublicDetailPeopleResponse findPublicDetailBy(final PeopleId peopleId) {
        final PeopleProfileJpaVO profile = Optional.ofNullable(
                queryFactory.select(people)
                    .from(people)
                    .where(people.id.eq(peopleId.getValue())
                        .and(people.profile.isNotNull()))
                    .fetchOne()
            )
            .orElseThrow(NotFoundPeopleException::new)
            .getProfile();

        final Tuple memberAndPeople = findMemberAndPeopleBy(peopleId)
            .orElseThrow(NotFoundPeopleException::new);

        return new PublicDetailPeopleResponse(
            peopleId.getValue(),
            memberAndPeople.get(member.nickname),
            memberAndPeople.get(member.profileImage),
            0,
            countPeopleLikePort.countBy(peopleId),
            mapper.mapToPublicPeopleProfileResponse(profile)
        );
    }
}