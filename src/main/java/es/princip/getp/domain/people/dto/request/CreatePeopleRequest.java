package es.princip.getp.domain.people.dto.request;

import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.domain.enums.PeopleType;
import es.princip.getp.global.validator.annotation.Enum;
import es.princip.getp.global.validator.annotation.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePeopleRequest(
    @NotBlank String nickname,
    @Email String email,
    @NotNull @PhoneNumber String phoneNumber,
    @Enum PeopleType peopleType,
    String profileImageUri
) {

    public People toEntity(final Member member) {
        return People.builder()
            .nickname(nickname)
            .email(email)
            .phoneNumber(phoneNumber)
            .peopleType(peopleType)
            .profileImageUri(profileImageUri)
            .member(member)
            .build();
    }
}