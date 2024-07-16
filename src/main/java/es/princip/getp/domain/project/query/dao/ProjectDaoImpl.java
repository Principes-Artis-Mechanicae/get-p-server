package es.princip.getp.domain.project.query.dao;

import com.querydsl.core.Tuple;
import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.common.domain.URI;
import es.princip.getp.domain.project.command.domain.AttachmentFile;
import es.princip.getp.domain.project.query.dto.CardProjectResponse;
import es.princip.getp.domain.project.query.dto.DetailProjectResponse;
import es.princip.getp.domain.project.query.dto.ProjectClientResponse;
import es.princip.getp.infra.support.QueryDslSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static es.princip.getp.domain.client.domain.QClient.client;
import static es.princip.getp.domain.member.domain.model.QMember.member;
import static es.princip.getp.domain.project.command.domain.QProject.project;

@Repository
public class ProjectDaoImpl extends QueryDslSupport implements ProjectDao {

    @Override
    public Page<CardProjectResponse> findCardProjectPage(Pageable pageable) {
        return applyPagination(pageable, getProjectContent(pageable), countQuery ->
            countQuery.select(project.count()).from(project)
        );
    }

    private List<CardProjectResponse> getProjectContent(Pageable pageable) {
        List<Tuple> result = getQueryFactory().select(
                project.projectId,
                project.title,
                project.payment,
                project.applicationDuration,
                project.hashtags,
                project.description,
                project.status
            )
            .from(project)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return result.stream()
            .map(tuple -> new CardProjectResponse(
                tuple.get(project.projectId),
                tuple.get(project.title),
                tuple.get(project.payment),
                0L,
                tuple.get(project.applicationDuration),
                hashtagsToString(tuple.get(project.hashtags)),
                tuple.get(project.description),
                tuple.get(project.status)
            ))
            .toList();
    }

    private List<String> hashtagsToString(List<Hashtag> hashtags) {
        return Optional.ofNullable(hashtags)
            .orElseGet(Collections::emptyList)
            .stream()
            .map(Hashtag::getValue)
            .toList();
    }

    private List<String> attachmentFilesToString(List<AttachmentFile> attachmentFiles) {
        return Optional.ofNullable(attachmentFiles)
            .orElseGet(Collections::emptyList)
            .stream()
            .map(AttachmentFile::getUri)
            .map(URI::getValue)
            .toList();
    }

    private ProjectClientResponse toProjectClientResponse(Tuple tuple) {
        return new ProjectClientResponse(
            tuple.get(client.clientId),
            tuple.get(member.nickname.value),
            tuple.get(client.address)
        );
    }

    @Override
    public Optional<DetailProjectResponse> findDetailProjectById(final Long projectId) {
        Tuple result = getQueryFactory().select(
                project.projectId,
                project.title,
                project.payment,
                project.applicationDuration,
                project.estimatedDuration,
                project.description,
                project.meetingType,
                project.category,
                project.status,
                project.attachmentFiles,
                project.hashtags,
                client.clientId,
                member.nickname,
                client.address
            )
            .from(project)
            .join(client).on(project.clientId.eq(client.clientId))
            .join(member).on(client.memberId.eq(member.memberId))
            .where(project.projectId.eq(projectId))
            .fetchOne();

        if (result == null) {
            return Optional.empty();
        }

        return Optional.of(new DetailProjectResponse(
            result.get(project.projectId),
            result.get(project.title),
            result.get(project.payment),
            result.get(project.applicationDuration),
            result.get(project.estimatedDuration),
            result.get(project.description),
            result.get(project.meetingType),
            result.get(project.category),
            result.get(project.status),
            attachmentFilesToString(result.get(project.attachmentFiles)),
            hashtagsToString(result.get(project.hashtags)),
            0L,
            toProjectClientResponse(result)
        ));
    }
}
