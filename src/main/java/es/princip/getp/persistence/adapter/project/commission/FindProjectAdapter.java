package es.princip.getp.persistence.adapter.project.commission;

import es.princip.getp.api.controller.common.dto.HashtagsResponse;
import es.princip.getp.api.controller.project.query.dto.AttachmentFilesResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.application.client.port.out.ClientQuery;
import es.princip.getp.application.like.project.port.out.CountProjectLikePort;
import es.princip.getp.application.project.commission.port.out.FindProjectPort;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.persistence.adapter.project.ProjectPersistenceMapper;
import es.princip.getp.persistence.adapter.project.apply.FindProjectApplicationAdapter;
import es.princip.getp.persistence.support.QueryDslSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static es.princip.getp.persistence.adapter.project.ProjectPersistenceUtil.toProjectIds;

@Repository
@RequiredArgsConstructor
class FindProjectAdapter extends QueryDslSupport implements FindProjectPort {

    private static final QProjectJpaEntity project = QProjectJpaEntity.projectJpaEntity;

    private final ClientQuery clientQuery;
    private final CountProjectLikePort projectLikeDao;
    private final FindProjectApplicationAdapter projectApplicationDao;
    private final ProjectPersistenceMapper mapper;

    private List<Project> getProjects(Pageable pageable) {
        return queryFactory.selectFrom(project)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(mapper::mapToDomain)
            .toList();
    }

    private List<ProjectCardResponse> assembleProjectCardResponse(
        final List<Project> projects,
        final Map<Long, Long> projectApplicationCounts
    ) {
        return projects.stream()
            .map(project -> ProjectCardResponse.of(
                project,
                projectApplicationCounts.getOrDefault(project.getProjectId(), 0L)
            ))
            .toList();
    }

    @Override
    public Page<ProjectCardResponse> findBy(Pageable pageable) {
        final List<Project> projects = getProjects(pageable);
        final Long[] projectIds = toProjectIds(projects);
        final Map<Long, Long> projectApplicationCounts = projectApplicationDao.countByProjectIds(projectIds);
        final List<ProjectCardResponse> content = assembleProjectCardResponse(projects, projectApplicationCounts);
        return applyPagination(
            pageable,
            content,
            countQuery -> countQuery.select(project.count()).from(project)
        );
    }

    @Override
    public ProjectDetailResponse findBy(final Long projectId) {
        final Project result = mapper.mapToDomain(Optional.ofNullable(
            queryFactory.selectFrom(project)
                .where(project.projectId.eq(projectId))
                .fetchOne()
            )
            .orElseThrow(NotFoundProjectException::new));
        final Long likesCount = projectLikeDao.countBy(projectId);

        return new ProjectDetailResponse(
            result.getProjectId(),
            result.getTitle(),
            result.getPayment(),
            result.getApplicationDuration(),
            result.getEstimatedDuration(),
            result.getDescription(),
            result.getMeetingType(),
            result.getCategory(),
            result.getStatus(),
            AttachmentFilesResponse.from(result.getAttachmentFiles()),
            HashtagsResponse.from(result.getHashtags()),
            likesCount,
            clientQuery.findProjectClientBy(result.getClientId())
        );
    }
}
