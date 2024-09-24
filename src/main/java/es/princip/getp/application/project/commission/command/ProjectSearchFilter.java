package es.princip.getp.application.project.commission.command;

import lombok.Getter;

@Getter
public class ProjectSearchFilter {

    private final boolean liked;
    private final boolean commissioned;
    private final boolean applied;
    private final boolean closed;

    public ProjectSearchFilter(
        final String liked,
        final String commissioned,
        final String applied,
        final String closed
    ) {
        this.liked = Boolean.parseBoolean(liked);
        this.commissioned = Boolean.parseBoolean(commissioned);
        this.applied = Boolean.parseBoolean(applied);
        this.closed = Boolean.parseBoolean(closed);
    }
}