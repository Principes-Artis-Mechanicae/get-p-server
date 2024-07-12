package es.princip.getp.domain.people.presentation.dto.request;

import es.princip.getp.domain.member.annotation.PhoneNumber;
import es.princip.getp.domain.member.domain.model.Nickname;
import es.princip.getp.domain.people.application.command.CreatePeopleCommand;
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

    public CreatePeopleCommand toCommand(Long memberId) {
        return CreatePeopleCommand.builder()
            .memberId(memberId)
            .nickname(Nickname.of(nickname))
            .email(es.princip.getp.domain.member.domain.model.Email.of(email))
            .phoneNumber(es.princip.getp.domain.member.domain.model.PhoneNumber.of(phoneNumber))
            .peopleType(peopleType)
            .build();
    }
}