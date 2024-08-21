package es.princip.getp.domain.people.query.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.MyPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.PublicDetailPeopleProfileResponse;
import es.princip.getp.common.util.QueryDslSupport;
import es.princip.getp.domain.like.query.dao.PeopleLikeDao;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleProfile;
import es.princip.getp.domain.people.command.domain.QPeople;
import es.princip.getp.domain.people.exception.NotFoundPeopleException;
import es.princip.getp.domain.people.exception.NotRegisteredPeopleProfileException;
import es.princip.getp.persistence.adapter.member.QMemberJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static es.princip.getp.domain.people.command.domain.QPeople.people;
import static es.princip.getp.domain.people.query.dao.PeopleDaoUtil.orderSpecifiersFromSort;
import static es.princip.getp.domain.people.query.dao.PeopleDaoUtil.toPeopleIds;
import static java.util.stream.Collectors.toMap;

@Repository
@RequiredArgsConstructor
// TODO: 조회 성능 개선 필요
public class PeopleQueryDslDao extends QueryDslSupport implements PeopleDao {

    private static final QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;

    private final PeopleLikeDao peopleLikeDao;

    private Map<Long, Tuple> findMemberAndPeopleByPeopleId(final Long... peopleId) {
        return queryFactory.select(
            member.nickname,
            member.profileImage,
            people.peopleId,
            people.info.peopleType
        )
        .from(people)
        .join(member)
        .on(people.memberId.eq(member.memberId))
        .where(people.peopleId.in(peopleId)).fetch().stream()
        .collect(toMap(tuple -> tuple.get(people.peopleId), Function.identity()));
    }

    private Optional<Tuple> findMemberAndPeopleByPeopleId(final Long peopleId) {
        return Optional.ofNullable(
            queryFactory.select(
                member.nickname,
                member.profileImage,
                people.peopleId,
                people.info.peopleType
            )
            .from(people)
            .join(member).on(people.memberId.eq(member.memberId))
            .where(people.peopleId.eq(peopleId))
            .fetchOne()
        );
    }

    private List<CardPeopleResponse> getCardPeopleContent(final Pageable pageable) {
        final List<People> result = queryFactory.selectFrom(people)
            .where(people.profile.isNotNull())
            .orderBy(orderSpecifiersFromSort(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        final Long[] peopleIds = toPeopleIds(result);

        final Map<Long, Long> likesCounts = peopleLikeDao.countByLikedIds(peopleIds);
        final Map<Long, Tuple> memberAndPeople = findMemberAndPeopleByPeopleId(peopleIds);

        return result.stream().map(people -> {
            final QPeople qPeople = QPeople.people;
            final Long peopleId = people.getPeopleId();
            return new CardPeopleResponse(
                peopleId,
                memberAndPeople.get(peopleId).get(member.nickname),
                memberAndPeople.get(peopleId).get(member.profileImage),
                memberAndPeople.get(peopleId).get(qPeople.info.peopleType),
                0,
                likesCounts.get(peopleId),
                CardPeopleProfileResponse.from(people.getProfile())
            );
        }).toList();
    }

    @Override
    public Page<CardPeopleResponse> findCardPeoplePage(final Pageable pageable) {
        return applyPagination(
            pageable,
            getCardPeopleContent(pageable),
            queryFactory -> queryFactory.select(people.count()).from(people)
        );
    }

    @Override
    public DetailPeopleResponse findDetailPeopleById(final Long peopleId) {
        final PeopleProfile profile = Optional.ofNullable(
                queryFactory.select(people)
                    .from(people)
                    .where(people.peopleId.eq(peopleId)
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
            memberAndPeople.get(people.info.peopleType),
            0,
            peopleLikeDao.countByLikedId(peopleId),
            DetailPeopleProfileResponse.from(profile)
        );
    }

    @Override
    public PublicDetailPeopleResponse findPublicDetailPeopleById(final Long peopleId) {
        final PeopleProfile profile = Optional.ofNullable(
                queryFactory.select(people)
                    .from(people)
                    .where(people.peopleId.eq(peopleId)
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
            memberAndPeople.get(people.info.peopleType),
            0,
            peopleLikeDao.countByLikedId(peopleId),
            PublicDetailPeopleProfileResponse.from(profile)
        );
    }

    @Override
    public MyPeopleResponse findByMemberId(final Long memberId) {
        return Optional.ofNullable(
            queryFactory.select(
                Projections.constructor(
                    MyPeopleResponse.class,
                    people.peopleId,
                    people.info.email.value,
                    member.nickname,
                    member.phoneNumber,
                    member.profileImage,
                    people.info.peopleType,
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
    public DetailPeopleProfileResponse findDetailPeopleProfileByMemberId(final Long memberId) {
        return DetailPeopleProfileResponse.from(
            Optional.ofNullable(
                queryFactory.select(people)
                    .from(people)
                    .where(people.memberId.eq(memberId)
                        .and(people.profile.isNotNull()))
                    .fetchOne()
            )
            .orElseThrow(NotRegisteredPeopleProfileException::new)
            .getProfile()
        );
    }
}
