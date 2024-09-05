package es.princip.getp.application.like.people.port.out;

import java.util.Map;

public interface CountPeopleLikePort {

    Long countBy(Long peopleId);

    Map<Long, Long> countBy(Long... peopleIds);
}
