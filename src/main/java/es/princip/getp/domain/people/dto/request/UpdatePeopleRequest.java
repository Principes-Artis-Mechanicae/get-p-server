package es.princip.getp.domain.people.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdatePeopleRequest(@NotNull String name, @NotNull String email,
                                  @NotNull String phoneNumber, @NotNull String peopleType,
                                  @NotNull String profileImageUri, @NotNull String accountNumber) {
}