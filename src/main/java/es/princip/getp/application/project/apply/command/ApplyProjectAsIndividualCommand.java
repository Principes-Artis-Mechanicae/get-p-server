package es.princip.getp.application.project.apply.command;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.apply.model.IndividualProjectApplicationData;
import es.princip.getp.domain.project.commission.model.ProjectId;

public class ApplyProjectAsIndividualCommand extends ApplyProjectCommand {

    public ApplyProjectAsIndividualCommand(
        final MemberId memberId,
        final ProjectId projectId,
        final IndividualProjectApplicationData data
    ) {
        super(memberId, projectId, data);
    }
}