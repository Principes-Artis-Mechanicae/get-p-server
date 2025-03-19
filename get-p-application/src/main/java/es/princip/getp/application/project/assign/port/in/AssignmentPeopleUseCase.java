package es.princip.getp.application.project.assign.port.in;

import es.princip.getp.application.project.assign.dto.command.AssignmentPeopleCommand;
import es.princip.getp.domain.project.confirmation.model.AssignmentPeopleId;

public interface AssignmentPeopleUseCase {
    AssignmentPeopleId assign(AssignmentPeopleCommand command);
}
