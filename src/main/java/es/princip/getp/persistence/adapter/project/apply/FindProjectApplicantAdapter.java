package es.princip.getp.persistence.adapter.project.apply;

import com.querydsl.core.Tuple;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.application.like.port.out.people.CountPeopleLikePort;
import es.princip.getp.application.project.apply.port.out.FindProjectApplicantPort;
import es.princip.getp.common.util.QueryDslSupport;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.persistence.adapter.member.QMemberJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static es.princip.getp.domain.people.command.domain.QPeople.people;
import static es.princip.getp.domain.people.query.dao.PeopleDaoUtil.orderSpecifiersFromSort;
import static es.princip.getp.domain.people.query.dao.PeopleDaoUtil.toPeopleIds;
import static java.util.stream.Collectors.toMap;

@Repository
@RequiredArgsConstructor
// TODO: 조회 성능 개선 필요
class FindProjectApplicantAdapter extends QueryDslSupport implements FindProjectApplicantPort {

    private static final QProjectApplicationJpaEntity projectApplication
        = QProjectApplicationJpaEntity.projectApplicationJpaEntity;
    private static final QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;

    private final CountPeopleLikePort peopleLikeDao;

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

    private List<Long> findApplicantIdByProjectId(final Long projectId) {
        return queryFactory.select(projectApplication.applicantId)
            .from(projectApplication)
            .where(projectApplication.projectId.eq(projectId))
            .fetch();
    }

    private List<People> findPagedApplicantByApplicantId(
        final List<Long> applicantIds,
        final Pageable pageable
    ) {
        return queryFactory.selectFrom(people)
            .where(people.peopleId.in(applicantIds))
            .orderBy(orderSpecifiersFromSort(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private List<DetailPeopleResponse> assemble(
        final List<People> applicants,
        final Map<Long, Long> likesCounts,
        final Map<Long, Tuple> memberAndPeople
    ) {
        return applicants.stream().map(applicant -> {
            final Long peopleId = applicant.getPeopleId();
            return new DetailPeopleResponse(
                peopleId,
                memberAndPeople.get(peopleId).get(member.nickname),
                memberAndPeople.get(peopleId).get(member.profileImage),
                memberAndPeople.get(peopleId).get(people.info.peopleType),
                0,
                likesCounts.get(peopleId),
                DetailPeopleProfileResponse.from(applicant.getProfile())
            );
        }).toList();
    }

    @Override
    public Page<DetailPeopleResponse> findBy(final Long projectId, final Pageable pageable) {
        final List<Long> applicantIds = findApplicantIdByProjectId(projectId);
        final List<People> applicants = findPagedApplicantByApplicantId(applicantIds, pageable);
        final Long[] peopleIds = toPeopleIds(applicants);
        final Map<Long, Long> likesCounts = peopleLikeDao.countByPeopleIds(peopleIds);
        final Map<Long, Tuple> memberAndPeople = findMemberAndPeopleByPeopleId(peopleIds);
        return applyPagination(
            pageable,
            assemble(applicants, likesCounts, memberAndPeople),
            queryFactory -> queryFactory.select(people.count())
                .from(people)
                .where(people.peopleId.in(applicantIds))
        );
    }
}
