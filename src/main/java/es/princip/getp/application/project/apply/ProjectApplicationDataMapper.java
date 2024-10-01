package es.princip.getp.application.project.apply;

import es.princip.getp.application.project.apply.command.ApplyProjectCommand;
import es.princip.getp.domain.project.apply.model.ProjectApplicationData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface ProjectApplicationDataMapper {

    ProjectApplicationData mapToData(ApplyProjectCommand command);
}
