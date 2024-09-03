package es.princip.getp.application.people.mapper;

import es.princip.getp.application.people.command.EditPeopleProfileCommand;
import es.princip.getp.application.people.command.RegisterPeopleProfileCommand;
import es.princip.getp.domain.people.model.PeopleProfileData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PeopleDataMapper {

    PeopleProfileData mapToData(RegisterPeopleProfileCommand command);

    PeopleProfileData mapToData(EditPeopleProfileCommand command);
}
