package es.princip.getp.application.project.commission.port.in;

import es.princip.getp.application.project.commission.dto.command.CommissionProjectCommand;
import es.princip.getp.domain.project.commission.model.ProjectId;

public interface CommissionProjectUseCase {

    ProjectId commission(CommissionProjectCommand command);
}
