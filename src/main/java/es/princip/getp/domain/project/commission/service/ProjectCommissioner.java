package es.princip.getp.domain.project.commission.service;

import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.common.service.ClockHolder;
import es.princip.getp.domain.project.commission.exception.ApplicationDurationNotBeforeEstimatedDurationException;
import es.princip.getp.domain.project.commission.exception.EndedApplicationDurationException;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectData;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
@RequiredArgsConstructor
public class ProjectCommissioner {

    private final ClockHolder clockHolder;

    public Project commissionProject(final ProjectData data) {
        final Clock clock = clockHolder.getClock();
        final Duration applicationDuration = data.applicationDuration();
        final Duration estimatedDuration = data.estimatedDuration();

        if (applicationDuration.isEnded(clock)) {
            throw new EndedApplicationDurationException();
        }
        if (!applicationDuration.isBefore(estimatedDuration)) {
            throw new ApplicationDurationNotBeforeEstimatedDurationException();
        }

        final ProjectStatus status = ProjectStatus.determineStatus(applicationDuration, clock);

        return buildProject(data, status);
    }

    private static Project buildProject(final ProjectData data, final ProjectStatus status) {
        return Project.builder()
            .title(data.title())
            .payment(data.payment())
            .applicationDuration(data.applicationDuration())
            .estimatedDuration(data.estimatedDuration())
            .description(data.description())
            .meetingType(data.meetingType())
            .category(data.category())
            .status(status)
            .clientId(data.clientId())
            .attachmentFiles(data.attachmentFiles())
            .hashtags(data.hashtags())
            .build();
    }
}
