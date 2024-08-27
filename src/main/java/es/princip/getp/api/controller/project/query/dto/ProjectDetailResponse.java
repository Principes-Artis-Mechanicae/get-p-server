package es.princip.getp.api.controller.project.query.dto;

import es.princip.getp.common.domain.Duration;
import es.princip.getp.common.dto.HashtagsResponse;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.ProjectCategory;
import es.princip.getp.domain.project.commission.model.ProjectStatus;

public record ProjectDetailResponse(
    Long projectId,
    String title,
    Long payment,
    Duration applicationDuration,
    Duration estimatedDuration,
    String description,
    MeetingType meetingType,
    ProjectCategory category,
    ProjectStatus status,
    AttachmentFilesResponse attachmentFiles,
    HashtagsResponse hashtags,
    Long likesCount,
    ProjectClientResponse client
) {
}
