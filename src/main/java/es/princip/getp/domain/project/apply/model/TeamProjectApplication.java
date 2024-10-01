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

import static es.princip.getp.domain.project.apply.model.ProjectApplicationStatus.COMPLETED;
import static es.princip.getp.domain.project.apply.model.ProjectApplicationStatus.PENDING_TEAM_APPROVAL;
import static es.princip.getp.domain.project.apply.model.TeammateStatus.APPROVED;

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

    public void approve(final People approver) {
        if (status != PENDING_TEAM_APPROVAL) {
            throw new IllegalStateException("팀원 승인 대기 상태가 아닙니다.");
        }
        final Teammate teammate = getTeammate(approver.getId());
        teammate.approve();
        if (isAllTeammatesApproved()) {
            status = COMPLETED;
        }
    }

    private boolean isAllTeammatesApproved() {
        return teammates.stream().allMatch(it -> it.getStatus() == APPROVED);
    }

    private Teammate getTeammate(final PeopleId peopleId) {
        return teammates.stream()
            .filter(it -> it.getPeopleId().equals(peopleId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 팀으로부터 승인 신청을 받지 않았습니다."));
    }
}