package es.princip.getp.domain.project.command.application;

import es.princip.getp.domain.project.command.application.command.CommissionProjectCommand;
import es.princip.getp.domain.project.command.domain.ProjectData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectDataMapper {

    ProjectData mapToData(Long clientId, CommissionProjectCommand command);
}
