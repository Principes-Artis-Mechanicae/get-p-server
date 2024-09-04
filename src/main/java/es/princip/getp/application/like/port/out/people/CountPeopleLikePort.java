package es.princip.getp.application.like.port.out.people;

import java.util.Map;

public interface CountPeopleLikePort {
    Long countByPeopleId(Long peopleId);

    Map<Long, Long> countByPeopleIds(Long... peopleIds);
}
