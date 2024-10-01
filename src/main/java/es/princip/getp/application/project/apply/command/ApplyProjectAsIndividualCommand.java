package es.princip.getp.application.project.apply.command;

import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.project.commission.model.ProjectId;

import java.util.List;

public class ApplyProjectAsIndividualCommand extends ApplyProjectCommand {

    public ApplyProjectAsIndividualCommand(
        final Member member,
        final ProjectId projectId,
        final Duration expectedDuration,
        final String description,
        final List<AttachmentFile> attachmentFiles
    ) {
        super(member, projectId, expectedDuration, description, attachmentFiles);
    }
}