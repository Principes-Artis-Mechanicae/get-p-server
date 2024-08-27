package es.princip.getp.persistence.adapter.project.meeting.entity;

import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "project_meeting")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectMeetingJpaEntity extends BaseTimeJpaEntity {

    @Id
    @Column(name = "project_meeting_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meetingId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "people_id")
    private Long peopleId;

    @Column(name = "location")
    private String location;

    @Embedded
    private MeetingScheduleJpaVO schedule;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "description")
    private String description;
}