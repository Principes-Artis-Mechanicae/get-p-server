package es.princip.getp.api.controller.project.command.dto.request;

import es.princip.getp.common.domain.Duration;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ApplyProjectRequest(
    @NotNull Duration expectedDuration,
    @NotNull String description,
    @NotNull List<String> attachmentFiles
) {
}
