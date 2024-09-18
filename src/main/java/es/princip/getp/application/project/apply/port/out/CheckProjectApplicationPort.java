package es.princip.getp.application.project.apply.port.out;

import es.princip.getp.domain.people.model.PeopleId;

public interface CheckProjectApplicationPort {

    boolean existsBy(PeopleId applicantId, Long projectId);
}
