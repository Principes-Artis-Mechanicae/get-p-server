package es.princip.getp.application.projectMeeting;

import es.princip.getp.application.projectMeeting.command.RegisterProjectCommand;
import es.princip.getp.domain.project.command.domain.ProjectData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectDataMapper {

    ProjectData mapToData(Long clientId, RegisterProjectCommand command);
}
