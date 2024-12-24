package es.princip.getp.application.project.assign.port.out;

import es.princip.getp.domain.project.confirmation.model.AssignmentPeople;

public interface SaveAssignmentPeoplePort {
    Long save(AssignmentPeople projectConfirmation);
}
