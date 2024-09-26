package es.princip.getp.application.people.command;

import lombok.Getter;

@Getter
public class PeopleSearchFilter {

    private final String keyword;
    private final boolean liked;

    public PeopleSearchFilter(final String keyword, final String liked) {
        this.keyword = keyword;
        this.liked = Boolean.parseBoolean(liked);
    }
}