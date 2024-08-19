package es.princip.getp.domain.project.command.domain;

import es.princip.getp.common.domain.BaseTimeEntity;
import es.princip.getp.common.domain.MeetingSchedule;
import es.princip.getp.domain.member.command.domain.model.PhoneNumber;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "project_meeting")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectMeeting extends BaseTimeEntity {

    @Id
    @Column(name = "project_meeting_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meetingId;

    // 프로젝트 ID
    @Column(name = "project_id")
    private Long projectId;

    // 지원자의 피플 ID
    @Column(name = "people_id")
    private Long applicantId;

    // 미팅 장소
    @Column(name = "location")
    private String location;

    // 미팅 일정
    @Embedded
    private MeetingSchedule schedule;

    // 연락처
    @Embedded
    private PhoneNumber phoneNumber;
    
    // 요구사항
    @Column(name = "description")
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
