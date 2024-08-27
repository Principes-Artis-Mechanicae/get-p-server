package es.princip.getp.persistence.adapter.project.apply;

import com.querydsl.jpa.impl.JPAQuery;
import es.princip.getp.api.controller.project.query.dto.AppliedProjectCardResponse;
import es.princip.getp.application.project.apply.port.out.FindAppliedProjectPort;
import es.princip.getp.common.util.QueryDslSupport;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.persistence.adapter.project.ProjectPersistenceMapper;
import es.princip.getp.persistence.adapter.project.commission.QProjectJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static es.princip.getp.domain.people.command.domain.QPeople.people;
import static es.princip.getp.domain.project.apply.model.QProjectApplication.projectApplication;
import static es.princip.getp.persistence.adapter.project.ProjectPersistenceUtil.toProjectIds;

@Repository
@RequiredArgsConstructor
class FindAppliedProjectAdapter extends QueryDslSupport implements FindAppliedProjectPort {

    private static final QProjectJpaEntity project = QProjectJpaEntity.projectJpaEntity;

    private final FindProjectApplicationAdapter findProjectApplicationAdapter;
    private final ProjectPersistenceMapper mapper;

    @Override
    public Page<AppliedProjectCardResponse> findBy(final Long memberId, final Pageable pageable) {
        final List<Project> projects = getContent(pageable, memberId);
        final Long[] projectIds = toProjectIds(projects);
        final Map<Long, Long> projectApplicationCounts
            = findProjectApplicationAdapter.countByProjectIds(projectIds);
        final List<AppliedProjectCardResponse> content = assemble(projects, projectApplicationCounts);
        return applyPagination(
            pageable,
            content,
            countQuery -> buildCountQuery(memberId)
        );
    }

    private JPAQuery<?> buildQuery(final Long memberId) {
        return queryFactory.from(project)
            .join(projectApplication).on(projectApplication.projectId.eq(project.projectId))
            .join(people).on(people.peopleId.eq(projectApplication.applicantId))
            .where(people.memberId.eq(memberId));
    }

    private List<Project> getContent(final Pageable pageable, final Long memberId) {
        return buildQuery(memberId).select(project)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(mapper::mapToDomain)
            .toList();
    }

    private JPAQuery<Long> buildCountQuery(final Long memberId) {
        return buildQuery(memberId).select(project.count());
    }

    private List<AppliedProjectCardResponse> assemble(
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
