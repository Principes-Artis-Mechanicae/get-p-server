package es.princip.getp.domain.people.dto.response.people;

import java.time.LocalDateTime;
import es.princip.getp.domain.people.dto.response.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.domain.people.entity.People;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DetailPeopleResponse(@NotNull Long peopleId, @NotNull String nickname, @NotNull String email,
                                    @NotNull String phoneNumber, @NotNull String peopleType, @NotNull String profileImageUri,
                                    @NotNull String accountNumber, @NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt, @Valid DetailPeopleProfileResponse profile) {

    public static DetailPeopleResponse from(People people, DetailPeopleProfileResponse profile) {
        return new DetailPeopleResponse(people.getPeopleId(), people.getNickname(), people.getEmail(),
            people.getPhoneNumber(), people.getPeopleType().name(), people.getProfileImageUri(),
            people.getAccountNumber(), people.getCreatedAt(), people.getUpdateAt(), profile);
    }
}