package es.princip.getp.persistence.adapter.people;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PeopleDetailResponse;
import es.princip.getp.application.like.people.port.out.CheckPeopleLikePort;
import es.princip.getp.application.like.people.port.out.CountPeopleLikePort;
import es.princip.getp.application.people.command.PeopleSearchFilter;
import es.princip.getp.application.people.port.out.FindPeoplePort;
import es.princip.getp.domain.member.model.Member;
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

import static es.princip.getp.persistence.adapter.member.MemberPersistenceUtil.memberIdEq;
import static es.princip.getp.persistence.adapter.people.PeoplePersistenceUtil.getPeopleIds;
import static java.util.stream.Collectors.toMap;

@Repository
@RequiredArgsConstructor
// TODO: 조회 성능 개선 필요
class FindPeopleAdapter extends QueryDslSupport implements FindPeoplePort {

    private static final QPeopleJpaEntity people = QPeopleJpaEntity.peopleJpaEntity;
    private static final QMemberJpaEntity qMember = QMemberJpaEntity.memberJpaEntity;
    private static final QPeopleLikeJpaEntity like = QPeopleLikeJpaEntity.peopleLikeJpaEntity;

    private final CheckPeopleLikePort checkPeopleLikePort;
    private final CountPeopleLikePort countPeopleLikePort;

    private final PeopleQueryMapper mapper;

    private Map<Long, Tuple> findMemberAndPeopleBy(final PeopleId... peopleIds) {
        final Long[] ids = Arrays.stream(peopleIds)
            .map(PeopleId::getValue)
            .toArray(Long[]::new);
        return queryFactory.select(
            qMember.nickname,
            qMember.profileImage,
            people.id
        )
        .from(people)
        .join(qMember)
        .on(people.memberId.eq(qMember.id))
        .where(people.id.in(ids)).fetch().stream()
        .collect(toMap(tuple -> tuple.get(people.id), Function.identity()));
    }

    private Optional<Tuple> findMemberAndPeopleBy(final PeopleId peopleId) {
        return Optional.ofNullable(
            queryFactory.select(
                qMember.nickname,
                qMember.profileImage,
                people.id
            )
            .from(people)
            .join(qMember).on(people.memberId.eq(qMember.id))
            .where(people.id.eq(peopleId.getValue()))
            .fetchOne()
        );
    }

    private <T> JPAQuery<T> buildQuery(
        final JPAQuery<T> selectFrom,
        final PeopleSearchFilter filter,
        final MemberId memberId
    ) {
        if (filter.isLiked()) {
            selectFrom.join(like).on(people.id.eq(like.peopleId))
                .join(qMember).on(like.memberId.eq(qMember.id))
                .where(memberIdEq(memberId));
        }
        if (!StringUtils.isAllBlank(filter.getKeyword())) {
            selectFrom.join(qMember).on(people.memberId.eq(qMember.id))
                .where(qMember.nickname.startsWith(filter.getKeyword()));
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
                memberAndPeople.get(peopleId).get(qMember.nickname),
                memberAndPeople.get(peopleId).get(qMember.profileImage),
                0,
                likesCounts.get(new PeopleId(peopleId)),
                mapper.mapToCardResponse(people.getProfile())
            );
        }).toList();
    }

    private static OrderSpecifier<?>[] getOrderSpecifiers(final Sort sort) {
        return sort.stream()
            .map(PeoplePersistenceUtil::getOrderSpecifier)
            .toArray(OrderSpecifier[]::new);
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

    private PeopleProfileJpaVO fetchPeopleProfile(final PeopleId peopleId) {
        return Optional.ofNullable(
            queryFactory.select(people)
                .from(people)
                .where(people.id.eq(peopleId.getValue())
                    .and(people.profile.isNotNull()))
                .fetchOne()
        )
        .orElseThrow(NotFoundPeopleException::new)
        .getProfile();
    }

    @Override
    public PeopleDetailResponse findDetailBy(final Member member, final PeopleId peopleId) {
        final PeopleProfileJpaVO profile = fetchPeopleProfile(peopleId);
        final Tuple memberAndPeople = findMemberAndPeopleBy(peopleId)
            .orElseThrow(NotFoundPeopleException::new);
        final long completedProjectsCount = 0;
        final Long likesCount = countPeopleLikePort.countBy(peopleId);
        final Boolean liked = checkPeopleLikePort.existsBy(member, peopleId);
        
        return new PeopleDetailResponse(
            peopleId.getValue(),
            memberAndPeople.get(qMember.nickname),
            memberAndPeople.get(qMember.profileImage),
            completedProjectsCount,
            likesCount,
            liked,
            mapper.mapToDetailResponse(profile)
        );
    }
}