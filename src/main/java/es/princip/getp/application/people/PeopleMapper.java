package es.princip.getp.application.people;

import es.princip.getp.domain.member.model.Email;
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
