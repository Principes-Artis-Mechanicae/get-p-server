package es.princip.getp.api.controller.project.query.dto;

import es.princip.getp.api.controller.common.dto.HashtagsResponse;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectCategory;

public record PublicProjectDetailResponse(
    Long projectId,
    String title,//
    Long payment,//
    Long recruitmentCount,//
    Long applicantsCount,//
    Duration applicationDuration,
    Duration estimatedDuration,
    String description,
    MeetingType meetingType,
    ProjectCategory category,
    HashtagsResponse hashtags,
    Long likesCount
) {
    public static PublicProjectDetailResponse of(
        Project project,
        Long applicantsCount,
        Long likesCount
    ) {
        return new PublicProjectDetailResponse(
            project.getId().getValue(),
            project.getTitle(),
            project.getPayment(),
            project.getRecruitmentCount(),
            applicantsCount,
            project.getApplicationDuration(),
            project.getEstimatedDuration(),
            "이 정보는 로그인 후에만 접근할 수 있습니다.",
            project.getMeetingType(),
            project.getCategory(),
            HashtagsResponse.from(project.getHashtags()),
            likesCount
        );
    }
}
