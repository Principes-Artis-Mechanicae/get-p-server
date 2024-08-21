package es.princip.getp.domain.project.command.domain;

import lombok.Getter;

@Getter
public class ProjectMeetingId {
    private Long value;

    public ProjectMeetingId(final Long value) {
        this.value = value;
    }
}
