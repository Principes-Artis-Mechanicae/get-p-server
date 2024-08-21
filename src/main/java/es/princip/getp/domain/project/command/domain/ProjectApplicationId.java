package es.princip.getp.domain.project.command.domain;

import lombok.Getter;

@Getter
public class ProjectApplicationId {
    private Long value;

    public ProjectApplicationId(final Long value) {
        this.value = value;
    }
}
