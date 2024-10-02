package es.princip.getp.domain.project.apply.model;

import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static es.princip.getp.domain.project.apply.model.ProjectApplicationStatus.PENDING_TEAM_APPROVAL;

@Getter
public class TeamProjectApplication extends ProjectApplication {

    public static final String TYPE = "TEAM";
    private final Set<@NotNull Teammate> teammates;

    @Builder
    public TeamProjectApplication(
        final ProjectApplicationId id,
        final PeopleId applicantId,
        final ProjectId projectId,
        final Duration expectedDuration,
        final ProjectApplicationStatus status,
        final String description,
        final List<AttachmentFile> attachmentFiles,
        final Set<Teammate> teammates,
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
        this.teammates = teammates;
        validate();
    }

    public static ProjectApplication of(
        final PeopleId applicantId,
        final ProjectId projectId,
        final ProjectApplicationData data,
        final Set<People> teammates
    ) {
        final Set<Teammate> mapped = teammates.stream()
            .map(teammate -> Teammate.builder()
                .peopleId(teammate.getId())
                .status(TeammateStatus.PENDING)
                .build())
            .collect(Collectors.toSet());
        return TeamProjectApplication.builder()
            .applicantId(applicantId)
            .projectId(projectId)
            .expectedDuration(data.getExpectedDuration())
            .description(data.getDescription())
            .attachmentFiles(data.getAttachmentFiles())
            .teammates(mapped)
            .status(PENDING_TEAM_APPROVAL)
            .build();
    }
}