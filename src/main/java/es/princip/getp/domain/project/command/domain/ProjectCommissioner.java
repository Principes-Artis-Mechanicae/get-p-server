package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.common.annotation.DomainService;
import es.princip.getp.domain.common.domain.ClockHolder;
import es.princip.getp.domain.common.domain.Duration;
import es.princip.getp.domain.project.exception.ApplicationDurationNotBeforeEstimatedDurationException;
import es.princip.getp.domain.project.exception.EndedApplicationDurationException;
import lombok.RequiredArgsConstructor;

import java.time.Clock;

@DomainService
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
