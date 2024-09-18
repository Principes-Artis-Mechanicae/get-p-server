package es.princip.getp.application.like.project.port.out;

import es.princip.getp.domain.like.project.model.ProjectLike;
import es.princip.getp.domain.people.model.PeopleId;

public interface LoadProjectLikePort {

    ProjectLike loadBy(PeopleId peopleId, Long projectId);
}
