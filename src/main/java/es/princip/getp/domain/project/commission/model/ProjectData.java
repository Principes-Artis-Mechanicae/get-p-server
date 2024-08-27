package es.princip.getp.domain.project.commission.model;

import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.common.model.Hashtag;

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