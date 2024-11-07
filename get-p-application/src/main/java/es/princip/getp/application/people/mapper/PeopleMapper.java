package es.princip.getp.application.people.mapper;

import es.princip.getp.domain.common.model.Email;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.People;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PeopleMapper {

    @Mapping(source = "email", target = "info.email")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    People mapToPeople(MemberId memberId, Email email);
}
