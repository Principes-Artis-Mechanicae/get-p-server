package es.princip.getp.api.controller.project.command.dto.request;

import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.ProjectCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CommissionProjectRequest(
    @NotBlank String title,
    @NotNull Long payment,
    @NotNull Long recruitmentCount,
    @NotNull Duration applicationDuration,
    @NotNull Duration estimatedDuration,
    @NotBlank String description,
    @NotNull MeetingType meetingType,
    @NotNull ProjectCategory category,
    @NotNull List<String> attachmentFiles,
    @NotNull List<String> hashtags
) {
}