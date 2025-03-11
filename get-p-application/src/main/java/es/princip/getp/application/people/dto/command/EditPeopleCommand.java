package es.princip.getp.application.people.dto.command;

import es.princip.getp.domain.common.model.Email;
import es.princip.getp.domain.common.model.PhoneNumber;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.member.model.Nickname;

public record EditPeopleCommand(
    MemberId memberId,
    Nickname nickname,
    Email email,
    PhoneNumber phoneNumber
) {
}