package es.princip.getp.application.project.commission.command;

import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.common.model.Hashtag;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.ProjectCategory;

import java.util.List;

public record CommissionProjectCommand(
    MemberId memberId,
    String title,
    Long payment,
    Long recruitmentCount,
    Duration applicationDuration,
    Duration estimatedDuration,
    String description,
    MeetingType meetingType,
    ProjectCategory category,
    List<AttachmentFile> attachmentFiles,
    List<Hashtag> hashtags
) {
}
