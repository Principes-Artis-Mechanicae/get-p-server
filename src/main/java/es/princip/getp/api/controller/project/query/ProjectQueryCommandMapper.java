package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.controller.common.mapper.CommandMapper;
import es.princip.getp.api.controller.common.mapper.HashtagMapper;
import es.princip.getp.api.controller.common.mapper.PhoneNumberMapper;
import es.princip.getp.api.controller.common.mapper.URLMapper;
import es.princip.getp.api.controller.people.command.PeopleCommandMapper;
import es.princip.getp.application.people.command.SearchTeammateCommand;
import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@CommandMapper
@Mapper(
    componentModel = "spring",
    uses = {URLMapper.class, HashtagMapper.class, PhoneNumberMapper.class, PeopleCommandMapper.class}
)
abstract class ProjectQueryCommandMapper {

    @Mapping(source = "projectId", target = "projectId.value")
    abstract SearchTeammateCommand mapToCommand(Long projectId, CursorPageable<? extends Cursor> pageable, String nickname);
}
