package es.princip.getp.domain.project.command.domain;

import es.princip.getp.common.domain.BaseTimeEntity;
import es.princip.getp.common.domain.MeetingSchedule;
import es.princip.getp.domain.member.command.domain.model.PhoneNumber;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectMeeting extends BaseTimeEntity {

    private Long meetingId;

    // 프로젝트 ID
    private Long projectId;

    // 지원자의 피플 ID
    private Long applicantId;

    // 미팅 장소
    private String location;

    // 미팅 일정
    private MeetingSchedule schedule;

    // 연락처
    private PhoneNumber phoneNumber;
    
    // 요구사항
    private String description;

    @Builder
    public ProjectMeeting(
        final Long projectId,
        final Long applicantId,
        final String location,
        final MeetingSchedule schedule,
        final PhoneNumber phoneNumber,
        final String description
    ) {
        this.projectId = projectId;
        this.applicantId = applicantId;
        this.location = location;
        this.schedule = schedule;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }
}
