package es.princip.getp.persistence.adapter.project.apply;

import com.querydsl.jpa.impl.JPAQuery;
import es.princip.getp.api.controller.project.query.dto.AppliedProjectCardResponse;
import es.princip.getp.application.project.apply.port.out.FindAppliedProjectPort;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.persistence.adapter.people.model.QPeopleJpaEntity;
import es.princip.getp.persistence.adapter.project.ProjectPersistenceMapper;
import es.princip.getp.persistence.adapter.project.commission.QProjectJpaEntity;
import es.princip.getp.persistence.support.QueryDslSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static es.princip.getp.persistence.adapter.project.ProjectPersistenceUtil.toProjectIds;

@Repository
@RequiredArgsConstructor
class FindAppliedProjectAdapter extends QueryDslSupport implements FindAppliedProjectPort {

    private static final QPeopleJpaEntity people = QPeopleJpaEntity.peopleJpaEntity;
    private static final QProjectApplicationJpaEntity projectApplication
        = QProjectApplicationJpaEntity.projectApplicationJpaEntity;
    private static final QProjectJpaEntity project = QProjectJpaEntity.projectJpaEntity;

    private final FindProjectApplicationAdapter findProjectApplicationAdapter;
    private final ProjectPersistenceMapper mapper;

    @Override
    public Page<AppliedProjectCardResponse> findBy(final MemberId memberId, final Pageable pageable) {
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

    private JPAQuery<?> buildQuery(final MemberId memberId) {
        return queryFactory.from(project)
            .join(projectApplication).on(projectApplication.projectId.eq(project.projectId))
            .join(people).on(people.id.eq(projectApplication.applicantId))
            .where(people.memberId.eq(memberId.getValue()));
    }

    private List<Project> getContent(final Pageable pageable, final MemberId memberId) {
        return buildQuery(memberId).select(project)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(mapper::mapToDomain)
            .toList();
    }

    private JPAQuery<Long> buildCountQuery(final MemberId memberId) {
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
