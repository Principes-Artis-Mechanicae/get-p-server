package es.princip.getp.domain.people.command.presentation.dto.request;

import es.princip.getp.domain.member.domain.model.Email;
import es.princip.getp.domain.member.domain.model.Nickname;
import es.princip.getp.domain.member.domain.model.PhoneNumber;
import es.princip.getp.domain.people.command.application.command.CreatePeopleCommand;
import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.infra.annotation.Enum;
import jakarta.validation.constraints.NotBlank;

public record CreatePeopleRequest(
    @NotBlank String nickname,
    @NotBlank String email,
    @NotBlank String phoneNumber,
    @Enum PeopleType peopleType
) {

    public CreatePeopleCommand toCommand(Long memberId) {
        return new CreatePeopleCommand(
            memberId,
            Nickname.of(nickname),
            Email.of(email),
            PhoneNumber.of(phoneNumber),
            peopleType
        );
    }
}