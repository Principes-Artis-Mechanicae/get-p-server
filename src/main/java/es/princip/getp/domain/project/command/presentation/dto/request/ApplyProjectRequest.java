package es.princip.getp.domain.project.command.presentation.dto.request;

import es.princip.getp.domain.common.domain.Duration;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ApplyProjectRequest(
    @NotNull Duration expectedDuration,
    @NotNull String description,
    @NotNull List<String> attachmentFiles
) {
}
