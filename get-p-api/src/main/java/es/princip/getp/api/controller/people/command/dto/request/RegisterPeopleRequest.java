package es.princip.getp.api.controller.people.command.dto.request;

import es.princip.getp.application.people.dto.command.RegisterPeopleCommand;
import es.princip.getp.domain.common.model.Email;
import es.princip.getp.domain.common.model.EmailPattern;
import es.princip.getp.domain.common.model.PhoneNumber;
import es.princip.getp.domain.common.model.PhoneNumberPattern;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.Nickname;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterPeopleRequest(
    @NotBlank String nickname,
    @EmailPattern String email,
    @NotNull @PhoneNumberPattern String phoneNumber
) {

    public RegisterPeopleCommand toCommand(final Member member) {
        return new RegisterPeopleCommand(
            member.getId(),
            Nickname.from(nickname),
            email == null ? member.getEmail() : Email.from(email),
            PhoneNumber.from(phoneNumber)
        );
    }
}