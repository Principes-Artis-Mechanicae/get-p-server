package es.princip.getp.application.project.apply.command;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.apply.model.ProjectApplicationData;
import es.princip.getp.domain.project.commission.model.ProjectId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class ApplyProjectCommand {

    private final MemberId memberId;
    private final ProjectId projectId;
    private final ProjectApplicationData data;
}