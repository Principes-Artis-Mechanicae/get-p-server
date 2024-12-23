package es.princip.getp.application.project.confirmation.port.out;

import es.princip.getp.domain.project.confirmation.model.ProjectConfirmation;

public interface SaveProjectConfirmationPort {
    Long save(ProjectConfirmation projectConfirmation);
}
