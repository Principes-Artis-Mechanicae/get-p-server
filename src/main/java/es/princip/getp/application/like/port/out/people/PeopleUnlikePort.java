package es.princip.getp.application.like.port.out.people;

import es.princip.getp.domain.like.model.people.PeopleLike;

public interface PeopleUnlikePort {
    void unlike(PeopleLike peopleLike);
}