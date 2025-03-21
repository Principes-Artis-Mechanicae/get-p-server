package es.princip.getp.persistence.adapter.project.apply.model;

import es.princip.getp.domain.project.apply.model.ProjectApplicationStatus;
import es.princip.getp.domain.project.apply.model.TeamProjectApplication;
import es.princip.getp.persistence.adapter.common.DurationJpaVO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "team_project_application")
@DiscriminatorValue(TeamProjectApplication.TYPE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamProjectApplicationJpaEntity extends ProjectApplicationJpaEntity {

    @OneToMany(
        mappedBy = "application",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private List<TeammateJpaEntity> teammates = new ArrayList<>();

    @Builder
    public TeamProjectApplicationJpaEntity(
        final Long id,
        final Long applicantId,
        final Long projectId,
        final DurationJpaVO expectedDuration,
        final ProjectApplicationStatus status,
        final String description,
        final List<String> attachmentFiles,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(
            id,
            applicantId,
            projectId,
            expectedDuration,
            status,
            description,
            attachmentFiles,
            createdAt,
            updatedAt
        );
    }

    public void addTeammate(TeammateJpaEntity teammate) {
        teammates.add(teammate);
    }
}
