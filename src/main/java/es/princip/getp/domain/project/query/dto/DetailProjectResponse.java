package es.princip.getp.domain.project.query.dto;

import es.princip.getp.domain.common.domain.Duration;
import es.princip.getp.domain.project.command.domain.MeetingType;
import es.princip.getp.domain.project.command.domain.ProjectCategory;
import es.princip.getp.domain.project.command.domain.ProjectStatus;

import java.util.List;

public record DetailProjectResponse(
    Long projectId,
    String title,
    Long payment,
    Duration applicationDuration,
    Duration estimatedDuration,
    String description,
    MeetingType meetingType,
    ProjectCategory category,
    ProjectStatus status,
    List<String> attachmentFiles,
    List<String> hashtags,
    Long interestCount,
    ProjectClientResponse client
) {
}
