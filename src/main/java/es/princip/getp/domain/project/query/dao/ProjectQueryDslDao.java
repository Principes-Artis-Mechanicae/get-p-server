package es.princip.getp.domain.project.query.dao;

import com.querydsl.core.types.Projections;
import es.princip.getp.domain.common.dto.HashtagsResponse;
import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.domain.project.query.dto.AttachmentFilesResponse;
import es.princip.getp.domain.project.query.dto.ProjectCardResponse;
import es.princip.getp.domain.project.query.dto.ProjectClientResponse;
import es.princip.getp.domain.project.query.dto.ProjectDetailResponse;
import es.princip.getp.infra.support.QueryDslSupport;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static es.princip.getp.domain.client.command.domain.QClient.client;
import static es.princip.getp.domain.member.command.domain.model.QMember.member;
import static es.princip.getp.domain.project.command.domain.QProject.project;
import static es.princip.getp.domain.project.query.dao.ProjectDaoHelper.toProjectIds;

@Repository
@RequiredArgsConstructor
public class ProjectQueryDslDao extends QueryDslSupport implements ProjectDao {

    private final ProjectLikeDao projectLikeDao;
    private final ProjectApplicationDao projectApplicationDao;

    private List<Project> getProjects(Pageable pageable) {
        return queryFactory.selectFrom(project)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private List<ProjectCardResponse> assembleProjectCardResponse(
        final List<Project> projects,
        final Map<Long, Long> projectApplicationCounts
    ) {
        return projects.stream()
            .map(project -> ProjectCardResponse.from(
                project,
                projectApplicationCounts.get(project.getProjectId())
            ))
            .toList();
    }

    @Override
    public Page<ProjectCardResponse> findPagedProjectCard(Pageable pageable) {
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

    private ProjectClientResponse getProjectClientResponseByClientId(final Long clientId) {
        return Optional.ofNullable(
            queryFactory.select(
                Projections.constructor(
                    ProjectClientResponse.class,
                    client.clientId,
                    member.nickname.value,
                    client.address
                )
            )
            .from(client)
            .join(member).on(client.memberId.eq(member.memberId))
            .where(client.clientId.eq(clientId))
            .fetchOne()
        )
        .orElseThrow(() -> new EntityNotFoundException("해당 의뢰자가 존재하지 않습니다."));
    }

    @Override
    public ProjectDetailResponse findProjectDetailById(final Long projectId) {
        final Project result = Optional.ofNullable(
            queryFactory.selectFrom(project)
                .where(project.projectId.eq(projectId))
                .fetchOne()
            )
            .orElseThrow(() -> new EntityNotFoundException("해당 프로젝트가 존재하지 않습니다."));
        final Long likesCount = projectLikeDao.countByProjectId(projectId);

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
            getProjectClientResponseByClientId(result.getClientId())
        );
    }
}
