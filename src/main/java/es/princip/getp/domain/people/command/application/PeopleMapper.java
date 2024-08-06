package es.princip.getp.domain.people.command.application;

import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleInfo;
import es.princip.getp.domain.people.command.domain.PeopleType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PeopleMapper {

    default People mapToPeople(Long memberId, Email email, PeopleType peopleType) {
        return new People(memberId, mapToInfo(email, peopleType));
    }

    PeopleInfo mapToInfo(Email email, PeopleType peopleType);
}
