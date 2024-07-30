package es.princip.getp.domain.project.query.dao;

import com.querydsl.core.types.Projections;
import es.princip.getp.domain.common.dto.HashtagsResponse;
import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.domain.project.query.dto.AttachmentFilesResponse;
import es.princip.getp.domain.project.query.dto.CardProjectResponse;
import es.princip.getp.domain.project.query.dto.DetailProjectResponse;
import es.princip.getp.domain.project.query.dto.ProjectClientResponse;
import es.princip.getp.infra.support.QueryDslSupport;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static es.princip.getp.domain.client.command.domain.QClient.client;
import static es.princip.getp.domain.member.command.domain.model.QMember.member;
import static es.princip.getp.domain.project.command.domain.QProject.project;

@Repository
public class ProjectDaoImpl extends QueryDslSupport implements ProjectDao {

    @Override
    public Page<CardProjectResponse> findCardProjectPage(Pageable pageable) {
        return applyPagination(
            pageable,
            getProjectContent(pageable),
            countQuery -> countQuery.select(project.count()).from(project)
        );
    }

    private List<CardProjectResponse> getProjectContent(Pageable pageable) {
        return queryFactory.selectFrom(project)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(CardProjectResponse::from)
            .toList();
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
    public DetailProjectResponse findDetailProjectById(final Long projectId) {
        final Project result = Optional.ofNullable(
            queryFactory.selectFrom(project)
                .where(project.projectId.eq(projectId))
                .fetchOne()
            )
            .orElseThrow(() -> new EntityNotFoundException("해당 프로젝트가 존재하지 않습니다."));

        return new DetailProjectResponse(
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
            0L,
            getProjectClientResponseByClientId(result.getClientId())
        );
    }

    @Override
    public boolean existsByProjectIdAndMemberId(Long projectId, Long memberId) {
        Long count = queryFactory.select(project.projectId.count())
            .from(project)
            .join(client)
            .on(
                project.clientId.eq(client.clientId)
                .and(project.projectId.eq(projectId))
                .and(client.memberId.eq(memberId))
            )
            .fetchOne();

        return count > 0;
    }
}