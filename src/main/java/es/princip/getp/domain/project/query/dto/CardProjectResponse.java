package es.princip.getp.domain.project.query.dto;

import es.princip.getp.domain.common.domain.Duration;
import es.princip.getp.domain.project.command.domain.ProjectStatus;

import java.util.List;

public record CardProjectResponse(
    Long projectId,
    String title,
    Long payment,
    Long applicantsCount,
    Duration applicationDuration,
    List<String> hashtags,
    String description,
    ProjectStatus status
) {
}
