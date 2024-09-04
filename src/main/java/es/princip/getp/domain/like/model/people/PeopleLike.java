package es.princip.getp.domain.like.model.people;

import java.time.LocalDateTime;

import es.princip.getp.domain.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeopleLike extends BaseEntity {

    private Long id;

    @NotNull private Long clientId;

    @NotNull private Long peopleId;

    @Builder
    public PeopleLike(
        final Long id,
        final Long clientId,
        final Long peopleId,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.id = id;
        this.clientId = clientId;
        this.peopleId = peopleId;
        validate();
    }
}