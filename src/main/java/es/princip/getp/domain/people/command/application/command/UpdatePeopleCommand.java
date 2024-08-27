package es.princip.getp.domain.people.command.application.command;

import es.princip.getp.domain.member.model.Email;
import es.princip.getp.domain.member.model.Nickname;
import es.princip.getp.domain.common.model.PhoneNumber;
import es.princip.getp.domain.people.command.domain.PeopleType;

public record UpdatePeopleCommand(
    Long memberId,
    Nickname nickname,
    Email email,
    PhoneNumber phoneNumber,
    PeopleType peopleType
) {
}