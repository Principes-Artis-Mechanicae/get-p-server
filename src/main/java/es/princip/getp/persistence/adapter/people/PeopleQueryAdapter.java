package es.princip.getp.persistence.adapter.people;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.MyPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.application.like.people.port.out.CheckPeopleLikePort;
import es.princip.getp.application.like.people.port.out.CountPeopleLikePort;
import es.princip.getp.application.people.port.out.FindMyPeoplePort;
import es.princip.getp.application.people.port.out.FindPeoplePort;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.exception.NotRegisteredPeopleProfileException;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.persistence.adapter.member.QMemberJpaEntity;
import es.princip.getp.persistence.adapter.people.mapper.PeopleQueryMapper;
import es.princip.getp.persistence.adapter.people.model.PeopleJpaEntity;
import es.princip.getp.persistence.adapter.people.model.PeopleProfileJpaVO;
import es.princip.getp.persistence.adapter.people.model.QPeopleJpaEntity;
import es.princip.getp.persistence.support.QueryDslSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static es.princip.getp.persistence.adapter.people.PeopleQueryUtil.orderSpecifiersFromSort;
import static es.princip.getp.persistence.adapter.people.PeopleQueryUtil.toPeopleIds;
import static java.util.stream.Collectors.toMap;

@Repository
@RequiredArgsConstructor
// TODO: 조회 성능 개선 필요
class PeopleQueryAdapter extends QueryDslSupport implements FindPeoplePort, FindMyPeoplePort {

    private static final QPeopleJpaEntity people = QPeopleJpaEntity.peopleJpaEntity;
    private static final QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;

    private final CheckPeopleLikePort checkPeopleLikePort;
    private final CountPeopleLikePort countPeopleLikePort;

    private final PeopleQueryMapper mapper;

    private Map<Long, Tuple> findMemberAndPeopleByPeopleId(final PeopleId... peopleIds) {
        final Long[] ids = Arrays.stream(peopleIds)
            .map(PeopleId::getValue)
            .toArray(Long[]::new);
        return queryFactory.select(
            member.nickname,
            member.profileImage,
            people.id,
            people.peopleType
        )
        .from(people)
        .join(member)
        .on(people.memberId.eq(member.id))
        .where(people.id.in(ids)).fetch().stream()
        .collect(toMap(tuple -> tuple.get(people.id), Function.identity()));
    }

    private Optional<Tuple> findMemberAndPeopleByPeopleId(final PeopleId peopleId) {
        return Optional.ofNullable(
            queryFactory.select(
                member.nickname,
                member.profileImage,
                people.id,
                people.peopleType
            )
            .from(people)
            .join(member).on(people.memberId.eq(member.id))
            .where(people.id.eq(peopleId.getValue()))
            .fetchOne()
        );
    }

    private List<CardPeopleResponse> getCardPeopleContent(final Pageable pageable) {
        final List<PeopleJpaEntity> result = queryFactory.selectFrom(people)
            .where(people.profile.isNotNull())
            .orderBy(orderSpecifiersFromSort(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        final PeopleId[] peopleIds = toPeopleIds(result);

        final Map<PeopleId, Long> likesCounts = countPeopleLikePort.countBy(peopleIds);
        final Map<Long, Tuple> memberAndPeople = findMemberAndPeopleByPeopleId(peopleIds);

        return result.stream().map(people -> {
            final Long peopleId = people.getId();
            return new CardPeopleResponse(
                peopleId,
                memberAndPeople.get(peopleId).get(member.nickname),
                memberAndPeople.get(peopleId).get(member.profileImage),
                memberAndPeople.get(peopleId).get(QPeopleJpaEntity.peopleJpaEntity.peopleType),
                0,
                likesCounts.get(new PeopleId(peopleId)),
                mapper.mapToCardPeopleProfileResponse(people.getProfile())
            );
        }).toList();
    }

    @Override
    public Page<CardPeopleResponse> findCardBy(final Pageable pageable) {
        return applyPagination(
            pageable,
            getCardPeopleContent(pageable),
            queryFactory -> queryFactory.select(people.count()).from(people)
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

        final Tuple memberAndPeople = findMemberAndPeopleByPeopleId(peopleId)
            .orElseThrow(NotFoundPeopleException::new);

        return new DetailPeopleResponse(
            peopleId.getValue(),
            memberAndPeople.get(member.nickname),
            memberAndPeople.get(member.profileImage),
            memberAndPeople.get(people.peopleType),
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

        final Tuple memberAndPeople = findMemberAndPeopleByPeopleId(peopleId)
            .orElseThrow(NotFoundPeopleException::new);

        return new PublicDetailPeopleResponse(
            peopleId.getValue(),
            memberAndPeople.get(member.nickname),
            memberAndPeople.get(member.profileImage),
            memberAndPeople.get(people.peopleType),
            0,
            countPeopleLikePort.countBy(peopleId),
            mapper.mapToPublicPeopleProfileResponse(profile)
        );
    }

    @Override
    public MyPeopleResponse findBy(final MemberId memberId) {
        return Optional.ofNullable(
            queryFactory.select(
                Projections.constructor(
                    MyPeopleResponse.class,
                    people.id,
                    people.email,
                    member.nickname,
                    member.phoneNumber,
                    member.profileImage,
                    people.peopleType,
                    Expressions.asNumber(0).as("completedProjectsCount"),
                    Expressions.asNumber(0).as("likesCount"),
                    people.createdAt,
                    people.updatedAt
                )
            )
            .from(people)
            .join(member).on(people.memberId.eq(member.id))
            .where(people.memberId.eq(memberId.getValue()))
            .fetchOne()
        ).orElseThrow(NotFoundPeopleException::new);
    }

    @Override
    public DetailPeopleProfileResponse findDetailProfileBy(final MemberId memberId) {
        final PeopleProfileJpaVO profile = Optional.ofNullable(
                queryFactory.select(people)
                    .from(people)
                    .where(people.memberId.eq(memberId.getValue())
                        .and(people.profile.isNotNull()))
                    .fetchOne()
            )
            .orElseThrow(NotRegisteredPeopleProfileException::new)
            .getProfile();
        return mapper.mapToDetailPeopleProfileResponse(profile);
    }
}