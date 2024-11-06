package es.princip.getp.application.project.apply.dto.command;

import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
public class ApplyProjectAsTeamCommand extends ApplyProjectCommand {

    private final Set<PeopleId> teammates;

    public ApplyProjectAsTeamCommand(
        final Member member,
        final ProjectId projectId,
        final Duration expectedDuration,
        final String description,
        final List<AttachmentFile> attachmentFiles,
        final Set<PeopleId> teammates
    ) {
        super(member, projectId, expectedDuration, description, attachmentFiles);
        this.teammates = teammates;
    }
}