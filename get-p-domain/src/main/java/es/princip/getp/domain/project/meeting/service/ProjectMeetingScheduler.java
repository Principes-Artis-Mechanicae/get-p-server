package es.princip.getp.domain.project.meeting.service;

import es.princip.getp.domain.common.model.MeetingSchedule;
import es.princip.getp.domain.common.model.PhoneNumber;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.meeting.exception.NotConfirmedProjectException;
import es.princip.getp.domain.project.meeting.model.ProjectMeeting;
import org.springframework.stereotype.Component;

@Component
public class ProjectMeetingScheduler {
    public ProjectMeeting schedule(
        final Project project,
        final People applicant,
        final ProjectApplication projectApplication,
        final String location,
        final MeetingSchedule schedule,
        final PhoneNumber phoneNumber,
        final String description
    ) {
        if (!projectApplication.isCompleted()) {
            throw new NotConfirmedProjectException();
        }

        return ProjectMeeting.builder()
            .projectId(project.getId())
            .applicantId(applicant.getId())
            .location(location)
            .schedule(schedule)
            .phoneNumber(phoneNumber)
            .description(description)
            .build();
    }
}
