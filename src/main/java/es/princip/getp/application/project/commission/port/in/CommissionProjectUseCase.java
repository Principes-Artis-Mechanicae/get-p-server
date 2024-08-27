package es.princip.getp.application.project.commission.port.in;

import es.princip.getp.application.project.commission.command.CommissionProjectCommand;

public interface CommissionProjectUseCase {

    Long commission(CommissionProjectCommand command);
}
