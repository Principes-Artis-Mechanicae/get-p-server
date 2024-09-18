package es.princip.getp.domain.like.project.model;

import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.support.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProjectLike extends BaseEntity {

    private final Long id;
    @NotNull private final PeopleId peopleId;
    @NotNull private final Long projectId;

    @Builder
    public ProjectLike(
        final Long id,
        final PeopleId peopleId,
        final Long projectId,
        final LocalDateTime createdAt
    ) {
        super(createdAt, null);

        this.id = id;
        this.peopleId = peopleId;
        this.projectId = projectId;

        validate();
    }
}