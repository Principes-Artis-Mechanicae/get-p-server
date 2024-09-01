package es.princip.getp.application.people.command;

import es.princip.getp.domain.common.model.PhoneNumber;
import es.princip.getp.domain.member.model.Email;
import es.princip.getp.domain.member.model.Nickname;
import es.princip.getp.domain.people.model.PeopleType;

public record RegisterPeopleCommand(
    Long memberId,
    Nickname nickname,
    Email email,
    PhoneNumber phoneNumber,
    PeopleType peopleType
) {
}