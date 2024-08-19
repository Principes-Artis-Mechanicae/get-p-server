package es.princip.getp.domain.project.command.application.command;

import es.princip.getp.common.domain.Duration;
import es.princip.getp.domain.project.command.domain.AttachmentFile;

import java.util.List;

public record ApplyProjectCommand(
    Long memberId,
    Long projectId,
    Duration expectedDuration,
    String description,
    List<AttachmentFile> attachmentFiles
) {
}
