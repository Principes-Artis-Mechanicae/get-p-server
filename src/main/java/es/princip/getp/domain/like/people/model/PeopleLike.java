package es.princip.getp.domain.like.people.model;

import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.support.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PeopleLike extends BaseEntity {

    private final Long id;
    @NotNull private final ClientId clientId;
    @NotNull private final PeopleId peopleId;

    @Builder
    public PeopleLike(
        final Long id,
        final ClientId clientId,
        final PeopleId peopleId,
        final LocalDateTime createdAt
    ) {
        super(createdAt, null);

        this.id = id;
        this.clientId = clientId;
        this.peopleId = peopleId;

        validate();
    }
}