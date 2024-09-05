package es.princip.getp.application.like.people.port.out;

import es.princip.getp.domain.like.people.model.PeopleLike;

public interface SavePeopleLikePort {

    void save(PeopleLike like);
}
