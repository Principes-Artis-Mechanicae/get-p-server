package es.princip.getp.domain.project.query.dao;

import com.querydsl.core.Tuple;
import es.princip.getp.domain.like.query.dao.PeopleLikeDao;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.QPeople;
import es.princip.getp.domain.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.domain.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.infra.support.QueryDslSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static es.princip.getp.domain.member.command.domain.model.QMember.member;
import static es.princip.getp.domain.people.command.domain.QPeople.people;
import static es.princip.getp.domain.people.query.dao.PeopleDaoUtil.orderSpecifiersFromSort;
import static es.princip.getp.domain.people.query.dao.PeopleDaoUtil.toPeopleIds;
import static es.princip.getp.domain.project.command.domain.QProjectApplication.projectApplication;
import static java.util.stream.Collectors.toMap;

@Repository
@RequiredArgsConstructor
// TODO: 조회 성능 개선 필요
public class ProjectApplicantQueryDslDao extends QueryDslSupport implements ProjectApplicantDao {

    private final PeopleLikeDao peopleLikeDao;

    private Map<Long, Tuple> findMemberAndPeopleByPeopleId(final Long... peopleId) {
        return queryFactory.select(
            member.nickname.value,
            member.profileImage.uri,
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
        final List<Long> applicantIds,
        final Pageable pageable
    ) {
        final List<People> result = findPagedApplicantByApplicantId(applicantIds, pageable);
        final Long[] peopleIds = toPeopleIds(result);
        final Map<Long, Long> likesCounts = peopleLikeDao.countByLikedIds(peopleIds);
        final Map<Long, Tuple> memberAndPeople = findMemberAndPeopleByPeopleId(peopleIds);

        return result.stream().map(people -> {
            final QPeople qPeople = QPeople.people;
            final Long peopleId = people.getPeopleId();
            return new DetailPeopleResponse(
                peopleId,
                memberAndPeople.get(peopleId).get(member.nickname.value),
                memberAndPeople.get(peopleId).get(member.profileImage.uri),
                memberAndPeople.get(peopleId).get(qPeople.info.peopleType),
                0,
                likesCounts.get(peopleId),
                DetailPeopleProfileResponse.from(people.getProfile())
            );
        }).toList();
    }

    @Override
    public Page<DetailPeopleResponse> findPagedApplicantByProjectId(final Long projectId, final Pageable pageable) {
        final List<Long> applicantIds = findApplicantIdByProjectId(projectId);
        return applyPagination(
            pageable,
            assemble(applicantIds, pageable),
            queryFactory -> queryFactory.select(people.count())
                .from(people)
                .where(people.peopleId.in(applicantIds))
        );
    }
}
