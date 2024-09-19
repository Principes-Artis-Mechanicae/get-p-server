package es.princip.getp.domain.project.meeting.model;

import es.princip.getp.domain.common.model.MeetingSchedule;
import es.princip.getp.domain.common.model.PhoneNumber;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.support.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProjectMeeting extends BaseEntity {

    private Long meetingId;
    @NotNull private ProjectId projectId;
    @NotNull private PeopleId applicantId;
    @NotBlank private String location;
    @NotNull private MeetingSchedule schedule;
    @NotNull private PhoneNumber phoneNumber;
    @NotBlank private String description;

    @Builder
    public ProjectMeeting(
        final Long meetingId,
        final ProjectId projectId,
        final PeopleId applicantId,
        final String location,
        final MeetingSchedule schedule,
        final PhoneNumber phoneNumber,
        final String description,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.meetingId = meetingId;
        this.projectId = projectId;
        this.applicantId = applicantId;
        this.location = location;
        this.schedule = schedule;
        this.phoneNumber = phoneNumber;
        this.description = description;

        validate();
    }
}
