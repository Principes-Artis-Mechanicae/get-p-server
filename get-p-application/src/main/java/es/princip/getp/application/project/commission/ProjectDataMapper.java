package es.princip.getp.application.project.commission;

import es.princip.getp.application.project.commission.dto.command.CommissionProjectCommand;
import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.project.commission.model.ProjectData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface ProjectDataMapper {

    ProjectData mapToData(ClientId clientId, CommissionProjectCommand command);
}
