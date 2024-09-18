package es.princip.getp.application.like.project.port.out;

import es.princip.getp.domain.people.model.PeopleId;

public interface CheckProjectLikePort {

    boolean existsBy(PeopleId peopleId, Long projectId);
}
