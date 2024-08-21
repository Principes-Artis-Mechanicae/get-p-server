package es.princip.getp.domain.project.command.domain;

import lombok.Getter;

@Getter
public class ProjectId {
    private Long value;

    public ProjectId(final Long value) {
        this.value = value;
    }
}
