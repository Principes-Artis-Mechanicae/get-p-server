package es.princip.getp.application.project.apply.command;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.apply.model.ProjectApplicationData;
import es.princip.getp.domain.project.commission.model.ProjectId;

public record ApplyProjectCommand(
    MemberId memberId,
    ProjectId projectId,
    ProjectApplicationData data
) {
}
