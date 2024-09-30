package es.princip.getp.application.project.apply.command;

import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.project.commission.model.ProjectId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public abstract class ApplyProjectCommand {

    private final Member member;
    private final ProjectId projectId;
    private final Duration expectedDuration;
    private final String description;
    private final List<AttachmentFile> attachmentFiles;
}