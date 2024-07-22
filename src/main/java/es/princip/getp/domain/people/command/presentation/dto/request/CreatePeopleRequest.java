package es.princip.getp.domain.people.command.presentation.dto.request;

import es.princip.getp.domain.member.command.annotation.EmailValid;
import es.princip.getp.domain.member.command.annotation.PhoneNumberValid;
import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.member.command.domain.model.Nickname;
import es.princip.getp.domain.member.command.domain.model.PhoneNumber;
import es.princip.getp.domain.people.command.application.command.CreatePeopleCommand;
import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.infra.annotation.Enum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePeopleRequest(
    @NotBlank String nickname,
    @NotNull @EmailValid String email,
    @NotNull @PhoneNumberValid String phoneNumber,
    @Enum PeopleType peopleType
) {

    public CreatePeopleCommand toCommand(final Long memberId) {
        return new CreatePeopleCommand(
            memberId,
            Nickname.of(nickname),
            Email.of(email),
            PhoneNumber.of(phoneNumber),
            peopleType
        );
    }
}