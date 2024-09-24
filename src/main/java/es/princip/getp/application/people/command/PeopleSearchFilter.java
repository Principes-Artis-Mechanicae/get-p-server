package es.princip.getp.application.people.command;

import lombok.Getter;

@Getter
public class PeopleSearchFilter {

    private final boolean liked;

    public PeopleSearchFilter(final String liked) {
        this.liked = Boolean.parseBoolean(liked);
    }
}