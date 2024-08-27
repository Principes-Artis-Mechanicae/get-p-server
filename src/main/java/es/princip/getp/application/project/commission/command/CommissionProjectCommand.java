package es.princip.getp.application.project.commission.command;

import es.princip.getp.common.domain.Duration;
import es.princip.getp.common.domain.Hashtag;
import es.princip.getp.domain.project.commission.model.AttachmentFile;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.ProjectCategory;

import java.util.List;

public record CommissionProjectCommand(
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
