package es.princip.getp.api.controller.project.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import es.princip.getp.api.controller.common.dto.HashtagsResponse;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectCategory;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDetailResponse {
    private final Long projectId;
    private final String title;
    private final Long payment;
    private final Long recruitmentCount;
    private final Long applicantsCount;
    private final Duration applicationDuration;
    private final Duration estimatedDuration;
    private String description;
    private final MeetingType meetingType;
    private final ProjectCategory category;
    private final ProjectStatus status;
    private AttachmentFilesResponse attachmentFiles;
    private final HashtagsResponse hashtags; 
    private final Long likesCount;
    private final Boolean liked;
    private ProjectClientResponse client;

    public ProjectDetailResponse(
        final Long projectId,
        final String title,
        final Long payment,
        final Long recruitmentCount,
        final Long applicantsCount,
        final Duration applicationDuration,
        final Duration estimatedDuration,
        final String description,
        final MeetingType meetingType,
        final ProjectCategory category,
        final ProjectStatus status,
        final AttachmentFilesResponse attachmentFiles,
        final HashtagsResponse hashtags,
        final Long likesCount,
        final Boolean liked,
        final ProjectClientResponse client
    ) {
        this.projectId = projectId;
        this.title = title;
        this.payment = payment;
        this.recruitmentCount = recruitmentCount;
        this.applicantsCount = applicantsCount;
        this.applicationDuration = applicationDuration;
        this.estimatedDuration = estimatedDuration;
        this.description = description;
        this.meetingType = meetingType;
        this.category = category;
        this.status = status;
        this.attachmentFiles = attachmentFiles;
        this.hashtags = hashtags;
        this.likesCount = likesCount;
        this.liked = liked;
        this.client = client;
    }

    public void mosaic(
        final String description,
        final AttachmentFilesResponse attachmentFiles,
        final ProjectClientResponse client
    ) {
        this.description = description;
        this.attachmentFiles = attachmentFiles;
        this.client = client;
    }

    public static ProjectDetailResponse of(
        final Project project,
        final Long applicantsCount,
        final Long likesCount,
        final Boolean liked,
        final ProjectClientResponse client
    ) {
        return new ProjectDetailResponse(
            project.getId().getValue(),
            project.getTitle(),
            project.getPayment(),
            project.getRecruitmentCount(),
            applicantsCount,
            project.getApplicationDuration(),
            project.getEstimatedDuration(),
            project.getDescription(),
            project.getMeetingType(),
            project.getCategory(),
            project.getStatus(),
            AttachmentFilesResponse.from(project.getAttachmentFiles()),
            HashtagsResponse.from(project.getHashtags()),
            likesCount,
            liked,
            client
        );
    }
}