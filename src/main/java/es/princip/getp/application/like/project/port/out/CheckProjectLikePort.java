package es.princip.getp.application.like.project.port.out;

import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.ProjectId;

public interface CheckProjectLikePort {

    boolean existsBy(PeopleId peopleId, ProjectId projectId);
}
