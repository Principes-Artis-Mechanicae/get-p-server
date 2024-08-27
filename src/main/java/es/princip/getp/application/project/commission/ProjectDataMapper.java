package es.princip.getp.application.project.commission;

import es.princip.getp.application.project.commission.command.CommissionProjectCommand;
import es.princip.getp.domain.project.commission.model.ProjectData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface ProjectDataMapper {

    ProjectData mapToData(Long clientId, CommissionProjectCommand command);
}
