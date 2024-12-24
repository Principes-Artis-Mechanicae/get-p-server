package es.princip.getp.domain.project.confirmation.model;

import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.support.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AssignmentPeople extends BaseEntity {

    private AssignmentPeopleId assignmentPeopleId;
    @NotNull private ProjectId projectId;
    @NotNull private PeopleId applicantId;

    @Builder
    public AssignmentPeople(
        final ProjectId projectId,
        final PeopleId applicantId,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.projectId = projectId;
        this.applicantId = applicantId;

        validate();
    }
}
