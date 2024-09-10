package es.princip.getp.domain.like.people.model;

import es.princip.getp.domain.support.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PeopleLike extends BaseEntity {

    private final Long id;
    @NotNull private final Long clientId;
    @NotNull private final Long peopleId;

    @Builder
    public PeopleLike(
        final Long id,
        final Long clientId,
        final Long peopleId,
        final LocalDateTime createdAt
    ) {
        super(createdAt, null);

        this.id = id;
        this.clientId = clientId;
        this.peopleId = peopleId;

        validate();
    }
}