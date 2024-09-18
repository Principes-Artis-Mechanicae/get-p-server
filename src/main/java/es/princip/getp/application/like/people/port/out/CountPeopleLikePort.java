package es.princip.getp.application.like.people.port.out;

import es.princip.getp.domain.people.model.PeopleId;

import java.util.Map;

public interface CountPeopleLikePort {

    Long countBy(PeopleId peopleId);

    Map<PeopleId, Long> countBy(PeopleId... peopleIds);
}
