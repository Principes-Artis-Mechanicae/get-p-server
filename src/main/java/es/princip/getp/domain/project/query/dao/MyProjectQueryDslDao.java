package es.princip.getp.domain.project.query.dao;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.domain.project.command.domain.ProjectStatus;
import es.princip.getp.domain.project.query.dto.MyProjectCardResponse;
import es.princip.getp.infra.support.QueryDslSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static es.princip.getp.domain.client.command.domain.QClient.client;
import static es.princip.getp.domain.project.command.domain.QProject.project;
import static es.princip.getp.domain.project.query.dao.ProjectDaoHelper.toProjectIds;

@Repository
@RequiredArgsConstructor
public class MyProjectQueryDslDao extends QueryDslSupport implements MyProjectDao {

    private final ProjectApplicationDao projectApplicationDao;

    @Override
    public Page<MyProjectCardResponse> findPagedMyProjectCard(
        final Pageable pageable,
        final Long memberId,
        final Boolean cancelled
    ) {
        final List<Project> projects = getMyProjects(pageable, memberId, cancelled);
        final Long[] projectIds = toProjectIds(projects);
        final Map<Long, Long> projectApplicationCounts = projectApplicationDao.countByProjectIds(projectIds);
        final List<MyProjectCardResponse> content = assembleProjectCardResponse(projects, projectApplicationCounts);

        return applyPagination(
            pageable,
            content,
            countQuery -> getMyProjectsCountQuery(memberId, cancelled)
        );
    }

    private List<MyProjectCardResponse> assembleProjectCardResponse(
        final List<Project> projects,
        final Map<Long, Long> projectApplicationCounts
    ) {
        return projects.stream()
            .map(project -> MyProjectCardResponse.of(
                project,
                projectApplicationCounts.get(project.getProjectId())
            ))
            .toList();
    }

    private JPAQuery<Long> getMyProjectsCountQuery(final Long memberId, final Boolean cancelled) {
        return queryFactory.select(project.count())
            .from(project)
            .join(client).on(project.clientId.eq(client.clientId))
            .where(client.memberId.eq(memberId), cancelledFilter(cancelled));
    }

    private List<Project> getMyProjects(final Pageable pageable, final Long memberId, final Boolean cancelled) {
        return queryFactory.selectFrom(project)
            .join(client).on(project.clientId.eq(client.clientId))
            .where(client.memberId.eq(memberId), cancelledFilter(cancelled))
            .orderBy(MyProjectDaoUtil.orderSpecifiersFromSort(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private BooleanExpression cancelledFilter(final Boolean cancelled) {
        return cancelled ? null : project.status.ne(ProjectStatus.CANCELLED);
    }
}
