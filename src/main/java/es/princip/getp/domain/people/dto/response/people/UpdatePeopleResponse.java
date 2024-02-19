package es.princip.getp.domain.people.dto.response.people;

import java.time.LocalDateTime;
import es.princip.getp.domain.people.entity.People;
import jakarta.validation.constraints.NotNull;

public record UpdatePeopleResponse(@NotNull Long peopleId, @NotNull String nickname, @NotNull String email,
                             @NotNull String phoneNumber, @NotNull String peopleType, @NotNull String profileImageUri,
                             @NotNull String accountNumber, @NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt) {

    public static UpdatePeopleResponse from(People people) {
        return new UpdatePeopleResponse(people.getPeopleId(), people.getNickname(), people.getEmail(),
            people.getPhoneNumber(), people.getPeopleType().name(), people.getProfileImageUri(),
            people.getAccountNumber(), people.getCreatedAt(), people.getUpdateAt());
    }
}