package es.princip.getp.domain.people.command.application;

import es.princip.getp.domain.people.command.application.command.EditPeopleProfileCommand;
import es.princip.getp.domain.people.command.application.command.RegisterPeopleProfileCommand;
import es.princip.getp.domain.people.command.domain.PeopleProfileData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PeopleDataMapper {

    PeopleProfileData mapToData(RegisterPeopleProfileCommand command);

    PeopleProfileData mapToData(EditPeopleProfileCommand command);
}
