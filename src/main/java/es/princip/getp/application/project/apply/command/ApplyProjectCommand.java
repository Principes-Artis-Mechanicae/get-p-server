package es.princip.getp.application.project.apply.command;

import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.member.model.MemberId;

import java.util.List;

public record ApplyProjectCommand(
    MemberId memberId,
    Long projectId,
    Duration expectedDuration,
    String description,
    List<AttachmentFile> attachmentFiles
) {
}
