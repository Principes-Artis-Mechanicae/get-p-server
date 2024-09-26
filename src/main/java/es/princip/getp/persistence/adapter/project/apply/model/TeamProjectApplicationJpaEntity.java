package es.princip.getp.persistence.adapter.project.apply.model;

import es.princip.getp.domain.project.apply.model.ProjectApplicationStatus;
import es.princip.getp.domain.project.apply.model.TeamProjectApplication;
import es.princip.getp.persistence.adapter.common.DurationJpaVO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "team_project_application")
@DiscriminatorValue(TeamProjectApplication.TYPE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamProjectApplicationJpaEntity extends ProjectApplicationJpaEntity {

    @ElementCollection
    @CollectionTable(
        name = "team_project_application_team",
        joinColumns = @JoinColumn(
            name = "project_application_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
        )
    )
    private List<Long> teams = new ArrayList<>();

    @Builder
    public TeamProjectApplicationJpaEntity(
        final Long id,
        final Long applicantId,
        final Long projectId,
        final DurationJpaVO expectedDuration,
        final ProjectApplicationStatus status,
        final String description,
        final List<String> attachmentFiles,
        final List<Long> teams
    ) {
        super(
            id,
            applicantId,
            projectId,
            expectedDuration,
            status,
            description,
            attachmentFiles
        );
        this.teams.addAll(teams);
    }
}
