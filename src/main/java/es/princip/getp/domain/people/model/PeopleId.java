package es.princip.getp.domain.people.model;

import lombok.Getter;

@Getter
public class PeopleId {
    private Long value;

    public PeopleId(final Long value) {
        this.value = value;
    }
}