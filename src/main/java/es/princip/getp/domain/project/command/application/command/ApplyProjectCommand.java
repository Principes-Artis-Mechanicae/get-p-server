package es.princip.getp.domain.project.command.application.command;

import es.princip.getp.domain.project.command.domain.AttachmentFile;
import es.princip.getp.domain.project.command.domain.ExpectedDuration;

import java.util.List;

public record ApplyProjectCommand(
    Long memberId,
    Long projectId,
    ExpectedDuration expectedDuration,
    String description,
    List<AttachmentFile> attachmentFiles
) {
}
