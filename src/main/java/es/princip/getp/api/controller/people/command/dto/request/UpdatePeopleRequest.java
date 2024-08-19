package es.princip.getp.api.controller.people.command.dto.request;

import es.princip.getp.common.annotation.Enum;
import es.princip.getp.api.validation.EmailPattern;
import es.princip.getp.api.validation.PhoneNumberPattern;
import es.princip.getp.domain.member.model.Email;
import es.princip.getp.domain.member.model.Nickname;
import es.princip.getp.domain.member.model.PhoneNumber;
import es.princip.getp.domain.people.command.application.command.UpdatePeopleCommand;
import es.princip.getp.domain.people.command.domain.PeopleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatePeopleRequest(
    @NotBlank String nickname,
    @NotNull @EmailPattern String email,
    @NotNull @PhoneNumberPattern String phoneNumber,
    @Enum PeopleType peopleType
) {

    public UpdatePeopleCommand toCommand(Long memberId) {
        return new UpdatePeopleCommand(
            memberId,
            Nickname.of(nickname),
            Email.of(email),
            PhoneNumber.of(phoneNumber),
            peopleType
        );
    }
}