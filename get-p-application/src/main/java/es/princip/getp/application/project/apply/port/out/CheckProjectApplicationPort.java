package es.princip.getp.application.project.apply.port.out;

import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.ProjectId;

public interface CheckProjectApplicationPort {

    boolean existsBy(PeopleId applicantId, ProjectId projectId);
}
