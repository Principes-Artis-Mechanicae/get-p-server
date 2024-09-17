package es.princip.getp.api.controller.people.command.dto.request;

import es.princip.getp.api.validation.Enum;
import es.princip.getp.application.people.command.RegisterPeopleCommand;
import es.princip.getp.domain.common.model.Email;
import es.princip.getp.domain.common.model.EmailPattern;
import es.princip.getp.domain.common.model.PhoneNumber;
import es.princip.getp.domain.common.model.PhoneNumberPattern;
import es.princip.getp.domain.member.model.MemberId;
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

    public RegisterPeopleCommand toCommand(final MemberId memberId) {
        return new RegisterPeopleCommand(
            memberId,
            Nickname.from(nickname),
            Email.from(email),
            PhoneNumber.from(phoneNumber),
            peopleType
        );
    }
}