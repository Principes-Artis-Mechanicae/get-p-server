package es.princip.getp.domain.people.dto.response;

import java.time.LocalDateTime;

import es.princip.getp.domain.people.entity.People;
import jakarta.validation.constraints.NotNull;

public record PeopleResponse(@NotNull Long peopleId, @NotNull String name, @NotNull String email,
                             @NotNull String phoneNumber, @NotNull String roleType,
                             @NotNull String profileImageUri,
                             @NotNull String accountNumber, @NotNull LocalDateTime createdAt,
                             @NotNull LocalDateTime updatedAt) {

    public static PeopleResponse from(People people) {
        return new PeopleResponse(people.getPeopleId(), people.getName(), people.getEmail(),
            people.getPhoneNumber(), people.getPeopleType().name(), people.getProfileImageUri(),
            people.getAccountNumber(), people.getCreatedAt(), people.getUpdateAt());
    }
}
