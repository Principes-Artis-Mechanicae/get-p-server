package es.princip.getp.persistence.adapter.people;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.MyPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.application.like.port.out.people.CountPeopleLikePort;
import es.princip.getp.application.people.port.out.FindMyPeoplePort;
import es.princip.getp.application.people.port.out.FindPeoplePort;
import es.princip.getp.common.util.QueryDslSupport;

import es.princip.getp.domain.people.exception.NotRegisteredPeopleProfileException;
import es.princip.getp.persistence.adapter.member.QMemberJpaEntity;
import es.princip.getp.persistence.adapter.people.mapper.PeopleQueryMapper;
import es.princip.getp.persistence.adapter.people.model.PeopleJpaEntity;
import es.princip.getp.persistence.adapter.people.model.PeopleProfileJpaVO;
import es.princip.getp.persistence.adapter.people.model.QPeopleJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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
public class PeopleQueryAdapter extends QueryDslSupport implements FindPeoplePort, FindMyPeoplePort {

    private static final QPeopleJpaEntity people = QPeopleJpaEntity.peopleJpaEntity;
    private static final QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;

    private final CountPeopleLikePort countPeopleLikePort;

    private final PeopleQueryMapper mapper;

    private Map<Long, Tuple> findMemberAndPeopleByPeopleId(final Long... peopleId) {
        return queryFactory.select(
            member.nickname,
            member.profileImage,
            people.id,
            people.peopleType
        )
        .from(people)
        .join(member)
        .on(people.memberId.eq(member.memberId))
        .where(people.id.in(peopleId)).fetch().stream()
        .collect(toMap(tuple -> tuple.get(people.id), Function.identity()));
    }

    private Optional<Tuple> findMemberAndPeopleByPeopleId(final Long peopleId) {
        return Optional.ofNullable(
            queryFactory.select(
                member.nickname,
                member.profileImage,
                people.id,
                people.peopleType
            )
            .from(people)
            .join(member).on(people.memberId.eq(member.memberId))
            .where(people.id.eq(peopleId))
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

        final Long[] peopleIds = toPeopleIds(result);

        final Map<Long, Long> likesCounts = countPeopleLikePort.countByPeopleIds(peopleIds);
        final Map<Long, Tuple> memberAndPeople = findMemberAndPeopleByPeopleId(peopleIds);

        return result.stream().map(people -> {
            final Long peopleId = people.getId();
            return new CardPeopleResponse(
                peopleId,
                memberAndPeople.get(peopleId).get(member.nickname),
                memberAndPeople.get(peopleId).get(member.profileImage),
                memberAndPeople.get(peopleId).get(QPeopleJpaEntity.peopleJpaEntity.peopleType),
                0,
                likesCounts.get(peopleId),
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
    public DetailPeopleResponse findDetailBy(final Long peopleId) {
        final PeopleProfileJpaVO profile = Optional.ofNullable(
                queryFactory.select(people)
                    .from(people)
                    .where(people.id.eq(peopleId)
                        .and(people.profile.isNotNull()))
                    .fetchOne()
            )
            .orElseThrow(NotFoundPeopleException::new)
            .getProfile();

        final Tuple memberAndPeople = findMemberAndPeopleByPeopleId(peopleId)
            .orElseThrow(NotFoundPeopleException::new);

        return new DetailPeopleResponse(
            peopleId,
            memberAndPeople.get(member.nickname),
            memberAndPeople.get(member.profileImage),
            memberAndPeople.get(people.peopleType),
            0,
            countPeopleLikePort.countByPeopleId(peopleId),
            mapper.mapToDetailPeopleProfileResponse(profile)
        );
    }

    @Override
    public PublicDetailPeopleResponse findPublicDetailBy(final Long peopleId) {
        final PeopleProfileJpaVO profile = Optional.ofNullable(
                queryFactory.select(people)
                    .from(people)
                    .where(people.id.eq(peopleId)
                        .and(people.profile.isNotNull()))
                    .fetchOne()
            )
            .orElseThrow(NotFoundPeopleException::new)
            .getProfile();

        final Tuple memberAndPeople = findMemberAndPeopleByPeopleId(peopleId)
            .orElseThrow(NotFoundPeopleException::new);

        return new PublicDetailPeopleResponse(
            peopleId,
            memberAndPeople.get(member.nickname),
            memberAndPeople.get(member.profileImage),
            memberAndPeople.get(people.peopleType),
            0,
            countPeopleLikePort.countByPeopleId(peopleId),
            mapper.mapToPublicPeopleProfileResponse(profile)
        );
    }

    @Override
    public MyPeopleResponse findBy(final Long memberId) {
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
            .join(member).on(people.memberId.eq(member.memberId))
            .where(people.memberId.eq(memberId))
            .fetchOne()
        ).orElseThrow(NotFoundPeopleException::new);
    }

    @Override
    public DetailPeopleProfileResponse findDetailProfileBy(final Long memberId) {
        final PeopleProfileJpaVO profile = Optional.ofNullable(
                queryFactory.select(people)
                    .from(people)
                    .where(people.memberId.eq(memberId)
                        .and(people.profile.isNotNull()))
                    .fetchOne()
            )
            .orElseThrow(NotRegisteredPeopleProfileException::new)
            .getProfile();
        return mapper.mapToDetailPeopleProfileResponse(profile);
    }
}