package es.princip.getp.domain.project.apply.model;

import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.support.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Teammate extends BaseEntity {

    private TeammateId id;
    @NotNull private final PeopleId peopleId;
    @NotNull private TeammateStatus status;

    @Builder
    public Teammate(
        final TeammateId id,
        final PeopleId peopleId,
        final TeammateStatus status,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);
        this.id = id;
        this.peopleId = peopleId;
        this.status = status;
        validate();
    }

    public void approve() {
        this.status = TeammateStatus.APPROVED;
    }

    public void reject() {
        this.status = TeammateStatus.REJECTED;
    }
}
