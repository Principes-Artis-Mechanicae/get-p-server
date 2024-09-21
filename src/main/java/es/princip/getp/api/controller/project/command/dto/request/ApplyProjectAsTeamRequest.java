package es.princip.getp.api.controller.project.command.dto.request;

import es.princip.getp.domain.common.model.Duration;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class ApplyProjectAsTeamRequest extends ApplyProjectRequest {

    private final @NotNull List<@NotNull Long> teams;

    public ApplyProjectAsTeamRequest(
        final String type,
        final Duration expectedDuration,
        final String description,
        final List<String> attachmentFiles,
        final List<Long> teams
    ) {
        super(type, expectedDuration, description, attachmentFiles);
        this.teams = teams;
    }
}