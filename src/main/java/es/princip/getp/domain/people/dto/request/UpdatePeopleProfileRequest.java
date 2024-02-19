package es.princip.getp.domain.people.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record UpdatePeopleProfileRequest(@NotNull String introduction, @NotNull String activityArea,
                                            @NotNull List<String> techStacks, @NotNull String education, @NotNull List<String> hashtags) {
}