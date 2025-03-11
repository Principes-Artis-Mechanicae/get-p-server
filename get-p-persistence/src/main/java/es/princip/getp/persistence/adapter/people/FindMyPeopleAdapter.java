package es.princip.getp.persistence.adapter.people;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import es.princip.getp.application.people.dto.response.people.MyPeopleResponse;
import es.princip.getp.application.people.dto.response.peopleProfile.PeopleProfileDetailResponse;
import es.princip.getp.application.people.exception.NotFoundPeopleException;
import es.princip.getp.application.people.port.out.FindMyPeoplePort;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.exception.NotRegisteredPeopleProfileException;
import es.princip.getp.persistence.adapter.member.QMemberJpaEntity;
import es.princip.getp.persistence.adapter.people.mapper.PeopleQueryMapper;
import es.princip.getp.persistence.adapter.people.model.PeopleProfileJpaVO;
import es.princip.getp.persistence.adapter.people.model.QPeopleJpaEntity;
import es.princip.getp.persistence.support.QueryDslSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
// TODO: 조회 성능 개선 필요
class FindMyPeopleAdapter extends QueryDslSupport implements FindMyPeoplePort {

    private static final QPeopleJpaEntity people = QPeopleJpaEntity.peopleJpaEntity;
    private static final QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;

    private final PeopleQueryMapper mapper;

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
    public PeopleProfileDetailResponse findDetailProfileBy(final MemberId memberId) {
        final PeopleProfileJpaVO profile = Optional.ofNullable(
                queryFactory.select(people)
                    .from(people)
                    .where(people.memberId.eq(memberId.getValue())
                        .and(people.profile.isNotNull()))
                    .fetchOne()
            )
            .orElseThrow(NotRegisteredPeopleProfileException::new)
            .getProfile();
        return mapper.mapToDetailResponse(profile);
    }
}