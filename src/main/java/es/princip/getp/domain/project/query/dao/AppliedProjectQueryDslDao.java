package es.princip.getp.domain.project.query.dao;

import com.querydsl.jpa.impl.JPAQuery;
import es.princip.getp.common.util.QueryDslSupport;
import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.api.controller.project.query.dto.AppliedProjectCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static es.princip.getp.domain.people.command.domain.QPeople.people;
import static es.princip.getp.domain.project.command.domain.QProject.project;
import static es.princip.getp.domain.project.command.domain.QProjectApplication.projectApplication;
import static es.princip.getp.domain.project.query.dao.ProjectDaoUtil.toProjectIds;

@Repository
@RequiredArgsConstructor
public class AppliedProjectQueryDslDao extends QueryDslSupport implements AppliedProjectDao{

    private final ProjectApplicationDao projectApplicationDao;
    
    @Override
    public Page<AppliedProjectCardResponse> findPagedMyAppliedProjects(
        final Pageable pageable,
        final Long memberId
    ) {
        final List<Project> projects = getAppliedProjects(pageable, memberId);
        final Long[] projectIds = toProjectIds(projects);
        final Map<Long, Long> projectApplicationCounts = projectApplicationDao.countByProjectIds(projectIds);
        final List<AppliedProjectCardResponse> content = assembleAppliedProjectCardResponse(projects, projectApplicationCounts);

        return applyPagination(
            pageable,
            content,
            countQuery -> getAppliedProjectsCountQuery(memberId)
        );
    }

    private List<Project> getAppliedProjects(
        final Pageable pageable,
        final Long memberId
    ) {
        return queryFactory.selectFrom(project)
            .join(projectApplication).on(projectApplication.projectId.eq(project.projectId))
            .join(people).on(people.peopleId.eq(projectApplication.applicantId))
            .where(people.memberId.eq(memberId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private JPAQuery<Long> getAppliedProjectsCountQuery(final Long memberId) {
        return queryFactory.select(project.count())
            .from(project)
            .join(projectApplication).on(projectApplication.projectId.eq(project.projectId))
            .join(people).on(people.peopleId.eq(projectApplication.applicantId))
            .where(people.memberId.eq(memberId));
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
}
