package es.princip.getp.domain.like.model.project;

import java.time.LocalDateTime;

import es.princip.getp.domain.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectLike extends BaseEntity {

    private Long id;

    @NotNull private Long peopleId;

    @NotNull private Long projectId;

    @Builder
    public ProjectLike(
        final Long id,
        final Long peopleId,
        final Long projectId,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.id = id;
        this.peopleId = peopleId;
        this.projectId = projectId;
        validate();
    }
}