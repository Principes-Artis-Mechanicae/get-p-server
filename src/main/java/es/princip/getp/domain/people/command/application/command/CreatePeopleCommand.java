package es.princip.getp.domain.people.command.application.command;

import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.member.command.domain.model.Nickname;
import es.princip.getp.domain.member.command.domain.model.PhoneNumber;
import es.princip.getp.domain.people.command.domain.PeopleType;

public record CreatePeopleCommand(
    Long memberId,
    Nickname nickname,
    Email email,
    PhoneNumber phoneNumber,
    PeopleType peopleType
) {
}