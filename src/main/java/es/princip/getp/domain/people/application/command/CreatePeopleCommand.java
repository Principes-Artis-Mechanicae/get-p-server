package es.princip.getp.domain.people.application.command;

import es.princip.getp.domain.member.domain.model.Email;
import es.princip.getp.domain.member.domain.model.Nickname;
import es.princip.getp.domain.member.domain.model.PhoneNumber;
import es.princip.getp.domain.people.domain.PeopleType;
import lombok.Builder;

@Builder
public record CreatePeopleCommand(
    Long memberId,
    Nickname nickname,
    Email email,
    PhoneNumber phoneNumber,
    PeopleType peopleType
) {
}