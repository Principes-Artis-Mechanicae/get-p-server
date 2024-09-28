package es.princip.getp.api.controller.project.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import es.princip.getp.api.controller.common.dto.HashtagsResponse;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectCategory;
import es.princip.getp.domain.project.commission.model.ProjectStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProjectDetailResponse(
    Long projectId,
    String title,
    Long payment,
    Long recruitmentCount,
    Long applicantsCount,
    Duration applicationDuration,
    Duration estimatedDuration,
    String description,
    MeetingType meetingType,
    ProjectCategory category,
    ProjectStatus status,
    AttachmentFilesResponse attachmentFiles,
    HashtagsResponse hashtags,
    Long likesCount,
    Boolean liked,
    ProjectClientResponse client
) {
    public static ProjectDetailResponse of(
            Project project,
            Long applicantsCount,
            Long likesCount,
            Boolean liked,
            ProjectClientResponse client
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