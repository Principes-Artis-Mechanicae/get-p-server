package es.princip.getp.domain.project.meeting.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class ProjectMeetingId {

    private final Long value;

    public ProjectMeetingId(final Long value) {
        this.value = value;
    }
}
