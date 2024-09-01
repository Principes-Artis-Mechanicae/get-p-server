package es.princip.getp.api.controller.people.command.dto.request;

import es.princip.getp.api.validation.EmailPattern;
import es.princip.getp.api.validation.Enum;
import es.princip.getp.api.validation.PhoneNumberPattern;
import es.princip.getp.application.people.command.RegisterPeopleCommand;
import es.princip.getp.domain.common.model.PhoneNumber;
import es.princip.getp.domain.member.model.Email;
import es.princip.getp.domain.member.model.Nickname;
import es.princip.getp.domain.people.model.PeopleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterPeopleRequest(
    @NotBlank String nickname,
    @NotNull @EmailPattern String email,
    @NotNull @PhoneNumberPattern String phoneNumber,
    @Enum PeopleType peopleType
) {

    public RegisterPeopleCommand toCommand(final Long memberId) {
        return new RegisterPeopleCommand(
            memberId,
            Nickname.of(nickname),
            Email.of(email),
            PhoneNumber.of(phoneNumber),
            peopleType
        );
    }
}