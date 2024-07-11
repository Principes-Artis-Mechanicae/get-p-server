package es.princip.getp.domain.people.dto.request;

import es.princip.getp.domain.member.annotation.PhoneNumber;
import es.princip.getp.domain.people.domain.PeopleType;
import es.princip.getp.infra.annotation.Enum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePeopleRequest(
    @NotBlank String nickname,
    @Email String email,
    @NotNull @PhoneNumber String phoneNumber,
    @Enum PeopleType peopleType
) {
}