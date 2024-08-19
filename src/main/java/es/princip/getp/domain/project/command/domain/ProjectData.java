package es.princip.getp.domain.project.command.domain;

import es.princip.getp.common.domain.Duration;
import es.princip.getp.common.domain.Hashtag;

import java.util.List;

public record ProjectData(
    String title,
    Long payment,
    Duration applicationDuration,
    Duration estimatedDuration,
    String description,
    MeetingType meetingType,
    ProjectCategory category,
    Long clientId,
    List<AttachmentFile> attachmentFiles,
    List<Hashtag> hashtags
) {
}