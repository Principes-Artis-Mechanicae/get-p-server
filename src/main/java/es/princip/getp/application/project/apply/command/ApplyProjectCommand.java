package es.princip.getp.application.project.apply.command;

import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;

import java.util.List;

public record ApplyProjectCommand(
    Long memberId,
    Long projectId,
    Duration expectedDuration,
    String description,
    List<AttachmentFile> attachmentFiles
) {
}
