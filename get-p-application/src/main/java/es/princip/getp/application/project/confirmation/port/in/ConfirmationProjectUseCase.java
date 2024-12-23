package es.princip.getp.application.project.confirmation.port.in;

import es.princip.getp.application.project.confirmation.dto.command.ConfirmationProjectCommand;
import es.princip.getp.domain.project.confirmation.model.ProjectConfirmationId;

public interface ConfirmationProjectUseCase {
    ProjectConfirmationId confirm(ConfirmationProjectCommand command);
}
