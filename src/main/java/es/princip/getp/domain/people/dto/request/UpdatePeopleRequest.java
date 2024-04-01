package es.princip.getp.domain.people.dto.request;

import es.princip.getp.domain.people.domain.enums.PeopleType;
import es.princip.getp.global.validator.annotation.Enum;
import es.princip.getp.global.validator.annotation.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatePeopleRequest(
    @NotBlank String nickname,
    @Email String email,
    @NotNull @PhoneNumber String phoneNumber,
    @Enum PeopleType peopleType,
    @NotNull String profileImageUri
) {
}