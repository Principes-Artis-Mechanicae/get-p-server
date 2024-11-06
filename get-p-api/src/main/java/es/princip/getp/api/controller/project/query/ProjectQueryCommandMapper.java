package es.princip.getp.api.controller.project.query;

import es.princip.getp.application.people.dto.command.SearchTeammateCommand;
import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
abstract class ProjectQueryCommandMapper {

    @Mapping(source = "projectId", target = "projectId.value")
    abstract SearchTeammateCommand mapToCommand(Long projectId, CursorPageable<? extends Cursor> pageable, String nickname);
}
