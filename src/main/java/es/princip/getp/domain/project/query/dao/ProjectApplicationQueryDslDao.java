package es.princip.getp.domain.project.query.dao;

import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.domain.project.command.domain.ProjectStatus;
import es.princip.getp.domain.project.query.dto.AppliedProjectCardResponse;
import es.princip.getp.infra.support.QueryDslSupport;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static es.princip.getp.domain.people.command.domain.QPeople.people;
import static es.princip.getp.domain.project.command.domain.QProject.project;
import static es.princip.getp.domain.project.command.domain.QProjectApplication.projectApplication;
import static es.princip.getp.domain.project.query.dao.ProjectDaoHelper.toProjectIds;

@Repository
@RequiredArgsConstructor
public class ProjectApplicationQueryDslDao extends QueryDslSupport implements ProjectApplicationDao {

    private final ProjectApplicationDao projectApplicationDao;

    @Override
    public Map<Long, Long> countByProjectIds(final Long... projectId) {
        return queryFactory.select(projectApplication.projectId, projectApplication.count())
            .from(projectApplication)
            .where(projectApplication.projectId.in(projectId))
            .groupBy(projectApplication.projectId)
            .fetch()
            .stream()
            .collect(Collectors.toMap(
                tuple -> tuple.get(projectApplication.projectId),
                tuple -> Optional.ofNullable(tuple.get(projectApplication.count()))
                    .orElse(0L)
            ));
    }

    private List<AppliedProjectCardResponse> assembleAppliedProjectCardResponse(
        final List<Project> projects,
        final Map<Long, Long> projectApplicationCounts
    ) {
        return projects.stream()
            .map(project -> AppliedProjectCardResponse.of(
                project,
                projectApplicationCounts.get(project.getProjectId())
            ))
            .toList();
    }

    @Override
    public Page<AppliedProjectCardResponse> findPagedMyAppliedProjects(
        final Pageable pageable,
        final Long memberId,
        final Boolean cancelled
    ) {
        final List<Project> projects = getMyProjects(pageable, memberId, cancelled);
        final Long[] projectIds = toProjectIds(projects);
        final Map<Long, Long> projectApplicationCounts = projectApplicationDao.countByProjectIds(projectIds);
        final List<AppliedProjectCardResponse> content = assembleAppliedProjectCardResponse(projects, projectApplicationCounts);

        return applyPagination(
            pageable,
            content,
            countQuery -> getMyProjectsCountQuery(memberId, cancelled)
        );
    }
    
    private List<Project> getMyProjects(
        final Pageable pageable,
        final Long memberId,
        final Boolean cancelled
    ) {
        return queryFactory.selectFrom(project)
            .join(projectApplication).on(projectApplication.applicantId.eq(people.peopleId))
            .join(projectApplication).on(projectApplication.projectId.eq(project.projectId))
            .where(people.memberId.eq(memberId), cancelledFilter(cancelled))
            .orderBy(MyProjectDaoUtil.orderSpecifiersFromSort(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private JPAQuery<Long> getMyProjectsCountQuery(final Long memberId, final Boolean cancelled) {
        return queryFactory.select(project.count())
            .from(project)
            .join(projectApplication).on(projectApplication.applicantId.eq(people.peopleId))
            .join(projectApplication).on(projectApplication.projectId.eq(project.projectId))
            .where(people.memberId.eq(memberId), cancelledFilter(cancelled));
    }

    private BooleanExpression cancelledFilter(final Boolean cancelled) {
        return cancelled ? null : project.status.ne(ProjectStatus.CANCELLED);
    }
}
