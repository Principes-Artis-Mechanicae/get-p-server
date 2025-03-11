package es.princip.getp.api.controller.project.command.dto.request;

import es.princip.getp.domain.common.model.Duration;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
public class ApplyProjectAsTeamRequest extends ApplyProjectRequest {

    private final @NotNull Set<@NotNull Long> teammates;

    public ApplyProjectAsTeamRequest(
        final String type,
        final Duration expectedDuration,
        final String description,
        final List<String> attachmentFiles,
        final Set<Long> teammates
    ) {
        super(type, expectedDuration, description, attachmentFiles);
        this.teammates = teammates;
    }
}