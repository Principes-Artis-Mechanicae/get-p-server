package es.princip.getp.application.projectMeeting.command;

import es.princip.getp.common.domain.Duration;
import es.princip.getp.common.domain.Hashtag;
import es.princip.getp.domain.project.command.domain.AttachmentFile;
import es.princip.getp.domain.project.command.domain.MeetingType;
import es.princip.getp.domain.project.command.domain.ProjectCategory;

import java.util.List;

public record RegisterProjectCommand(
    Long memberId,
    String title,
    Long payment,
    Duration applicationDuration,
    Duration estimatedDuration,
    String description,
    MeetingType meetingType,
    ProjectCategory category,
    List<AttachmentFile> attachmentFiles,
    List<Hashtag> hashtags
) {
}
