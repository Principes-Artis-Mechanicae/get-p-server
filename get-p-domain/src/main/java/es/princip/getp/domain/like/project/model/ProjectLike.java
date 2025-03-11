package es.princip.getp.domain.like.project.model;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.support.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProjectLike extends BaseEntity {

    private final Long id;
    @NotNull private final MemberId memberId;
    @NotNull private final ProjectId projectId;

    @Builder
    public ProjectLike(
        final Long id,
        final MemberId memberId,
        final ProjectId projectId,
        final LocalDateTime createdAt
    ) {
        super(createdAt, null);

        this.id = id;
        this.memberId = memberId;
        this.projectId = projectId;

        validate();
    }
}