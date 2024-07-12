package es.princip.getp.domain.project.dto.request;

import es.princip.getp.domain.project.domain.ExpectedDuration;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ApplyProjectRequest(
    @NotNull ExpectedDuration expectedDuration,
    @NotNull String description,
    @NotNull List<String> attachmentUris
) {

}
