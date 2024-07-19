package es.princip.getp.domain.project.command.presentation.dto.request;

import es.princip.getp.domain.common.domain.Duration;
import es.princip.getp.domain.project.command.domain.MeetingType;
import es.princip.getp.domain.project.command.domain.ProjectCategory;
import es.princip.getp.infra.annotation.Enum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RegisterProjectRequest(
    @NotBlank String title,
    @NotNull Long payment,
    @NotNull Duration applicationDuration,
    @NotNull Duration estimatedDuration,
    @NotBlank String description,
    @Enum MeetingType meetingType,
    @Enum ProjectCategory category,
    @NotNull List<String> attachmentUris,
    @NotNull List<String> hashtags
) {
}