package es.princip.getp.domain.project.apply.model;

import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TeamProjectApplication extends ProjectApplication {

    public static final String TYPE = "TEAM";
    private final List<@NotNull PeopleId> teams = new ArrayList<>();

    @Builder
    public TeamProjectApplication(
        final ProjectApplicationId id,
        final PeopleId applicantId,
        final ProjectId projectId,
        final Duration expectedDuration,
        final ProjectApplicationStatus status,
        final String description,
        final List<AttachmentFile> attachmentFiles,
        final List<PeopleId> teams,
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
        this.teams.addAll(teams);
        validate();
    }
}