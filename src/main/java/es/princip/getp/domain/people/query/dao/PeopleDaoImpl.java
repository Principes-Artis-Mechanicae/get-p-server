package es.princip.getp.domain.people.query.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import es.princip.getp.domain.people.command.domain.PeopleProfile;
import es.princip.getp.domain.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.domain.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.domain.people.query.dto.people.PeopleResponse;
import es.princip.getp.domain.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.domain.people.query.dto.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.domain.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.domain.people.query.dto.peopleProfile.PublicDetailPeopleProfileResponse;
import es.princip.getp.infra.support.QueryDslSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static es.princip.getp.domain.member.command.domain.model.QMember.member;
import static es.princip.getp.domain.people.command.domain.QPeople.people;
import static es.princip.getp.domain.people.command.domain.QPeopleProfile.peopleProfile;
import static es.princip.getp.domain.people.query.dao.PeoplePaginationHelper.getPeopleOrderSpecifiers;
import static java.util.stream.Collectors.toMap;

@Repository
public class PeopleDaoImpl extends QueryDslSupport implements PeopleDao {

    private Map<Long, Tuple> findMemberAndPeopleByPeopleId(Long... peopleId) {
        return queryFactory.select(
            member.nickname.value,
            member.profileImage.uri,
            people.peopleId,
            people.peopleType
        )
        .from(people)
        .join(member)
        .on(people.memberId.eq(member.memberId))
        .where(people.peopleId.in(peopleId)).fetch().stream()
        .collect(toMap(tuple -> tuple.get(people.peopleId), Function.identity()));
    }

    private Tuple findMemberAndPeopleByPeopleId(final Long peopleId) {
        return Optional.ofNullable(
            queryFactory.select(
                member.nickname.value,
                member.profileImage.uri,
                people.peopleId, people.peopleType
            )
            .from(people)
            .join(member).on(people.memberId.eq(member.memberId))
            .where(people.peopleId.eq(peopleId))
            .fetchOne()
        )
        .orElseThrow();
    }

    private List<CardPeopleResponse> getCardPeopleContent(Pageable pageable) {
        List<PeopleProfile> result = queryFactory.selectFrom(peopleProfile)
            .orderBy(getPeopleOrderSpecifiers(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long[] peopleIds = result.stream()
            .map(PeopleProfile::getPeopleId)
            .toArray(Long[]::new);

        Map<Long, Tuple> memberAndPeople = findMemberAndPeopleByPeopleId(peopleIds);

        return result.stream().map(profile -> {
            final Long peopleId = profile.getPeopleId();
            return new CardPeopleResponse(
                peopleId,
                memberAndPeople.get(peopleId).get(member.nickname.value),
                memberAndPeople.get(peopleId).get(member.profileImage.uri),
                memberAndPeople.get(peopleId).get(people.peopleType),
                0,
                0,
                CardPeopleProfileResponse.from(profile)
            );
        }).toList();
    }

    @Override
    public Page<CardPeopleResponse> findCardPeoplePage(Pageable pageable) {
        return applyPagination(
            pageable,
            getCardPeopleContent(pageable),
            queryFactory -> queryFactory.select(peopleProfile.count()).from(peopleProfile)
        );
    }

    @Override
    public DetailPeopleResponse findDetailPeopleById(Long peopleId) {
        PeopleProfile result = Optional.ofNullable(
                queryFactory.selectFrom(peopleProfile)
                    .where(peopleProfile.peopleId.eq(peopleId))
                    .fetchOne()
            )
            .orElseThrow();

        Tuple memberAndPeople = findMemberAndPeopleByPeopleId(peopleId);

        return new DetailPeopleResponse(
            peopleId,
            memberAndPeople.get(member.nickname.value),
            memberAndPeople.get(member.profileImage.uri),
            memberAndPeople.get(people.peopleType),
            0,
            0,
            DetailPeopleProfileResponse.from(result)
        );
    }

    @Override
    public PublicDetailPeopleResponse findPublicDetailPeopleById(Long peopleId) {
        final PeopleProfile profile = Optional.ofNullable(
                queryFactory.selectFrom(peopleProfile)
                    .where(peopleProfile.peopleId.eq(peopleId))
                    .fetchOne()
            )
            .orElseThrow();

        final Tuple memberAndPeople = findMemberAndPeopleByPeopleId(peopleId);

        return new PublicDetailPeopleResponse(
            peopleId,
            memberAndPeople.get(member.nickname.value),
            memberAndPeople.get(member.profileImage.uri),
            memberAndPeople.get(people.peopleType),
            0,
            0,
            PublicDetailPeopleProfileResponse.from(profile)
        );
    }

    @Override
    public PeopleResponse findByMemberId(final Long memberId) {
        return Optional.ofNullable(
            queryFactory.select(
                Projections.constructor(
                    PeopleResponse.class,
                    people.peopleId,
                    people.email.value,
                    member.nickname.value,
                    member.profileImage.uri,
                    people.peopleType,
                    Expressions.asNumber(0).as("completedProjectsCount"),
                    Expressions.asNumber(0).as("interestsCount"),
                    people.createdAt,
                    people.updatedAt
                )
            )
            .from(people)
            .join(member).on(people.memberId.eq(member.memberId))
            .where(people.memberId.eq(memberId))
            .fetchOne()
        )
        .orElseThrow();
    }

    @Override
    public DetailPeopleProfileResponse findDetailPeopleProfileByMemberId(final Long memberId) {
        return DetailPeopleProfileResponse.from(
            Optional.ofNullable(
                queryFactory.selectFrom(peopleProfile)
                    .where(peopleProfile.peopleId.eq(memberId))
                    .fetchOne()
            )
            .orElseThrow()
        );
    }
}
