package es.princip.getp.domain.people.command.presentation.dto.request;

import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.member.command.domain.model.Nickname;
import es.princip.getp.domain.member.command.domain.model.PhoneNumber;
import es.princip.getp.domain.people.command.application.command.UpdatePeopleCommand;
import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.infra.annotation.Enum;
import jakarta.validation.constraints.NotBlank;

public record UpdatePeopleRequest(
    @NotBlank String nickname,
    @NotBlank String email,
    @NotBlank String phoneNumber,
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