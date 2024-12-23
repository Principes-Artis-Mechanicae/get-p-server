package es.princip.getp.application.project.confirmation.dto.command;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.ProjectId;

public record ConfirmationProjectCommand(
    MemberId memberId,
    ProjectId projectId,
    PeopleId applicantId
) {}
