package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.common.domain.ClockHolder;
import es.princip.getp.domain.common.domain.Duration;
import es.princip.getp.domain.project.exception.ApplicationDurationIsEndedException;
import es.princip.getp.domain.project.exception.ApplicationDurationIsNotBeforeEstimatedDurationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
@RequiredArgsConstructor
public class ProjectRegister {

    private final ClockHolder clockHolder;

    public Project registerProject(final ProjectData data) {
        final Clock clock = clockHolder.getClock();
        final Duration applicationDuration = data.applicationDuration();
        final Duration estimatedDuration = data.estimatedDuration();

        if (applicationDuration.isEnded(clock)) {
            throw new ApplicationDurationIsEndedException();
        }
        if (!applicationDuration.isBefore(estimatedDuration)) {
            throw new ApplicationDurationIsNotBeforeEstimatedDurationException();
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