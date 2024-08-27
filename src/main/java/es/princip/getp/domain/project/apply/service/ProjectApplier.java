package es.princip.getp.domain.project.apply.service;

import es.princip.getp.common.domain.ClockHolder;
import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.exception.NotRegisteredPeopleProfileException;
import es.princip.getp.domain.project.apply.ProjectApplicationRepository;
import es.princip.getp.domain.project.apply.exception.AlreadyAppliedProjectException;
import es.princip.getp.domain.project.apply.exception.ClosedProjectApplicationException;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationStatus;
import es.princip.getp.domain.project.commission.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectApplier {

    private final ProjectApplicationRepository projectApplicationRepository;
    private final ClockHolder clockHolder;

    /**
     * 프로젝트 지원
     *
     * @param people 지원하는 피플
     * @param project 지원할 프로젝트
     * @param expectedDuration 예상 기간
     * @param description 설명
     * @param attachmentFiles 첨부 파일
     * @return 프로젝트 지원
     */
    public ProjectApplication applyForProject(
        final People people,
        final Project project,
        final Duration expectedDuration,
        final String description,
        final List<AttachmentFile> attachmentFiles
    ) {
        final Long peopleId = people.getPeopleId();
        final Long projectId = project.getProjectId();
        if (projectApplicationRepository.existsByApplicantIdAndProjectId(peopleId, projectId)) {
            throw new AlreadyAppliedProjectException();
        }
        final Clock clock = clockHolder.getClock();
        if (project.isApplicationClosed(clock)) {
            throw new ClosedProjectApplicationException();
        }
        if (!people.isProfileRegistered()) {
            throw new NotRegisteredPeopleProfileException();
        }
        final ProjectApplication application = ProjectApplication.builder()
            .applicantId(people.getPeopleId())
            .projectId(project.getProjectId())
            .expectedDuration(expectedDuration)
            .description(description)
            .attachmentFiles(attachmentFiles)
            .applicationStatus(ProjectApplicationStatus.APPLICATION_COMPLETED)
            .build();
        projectApplicationRepository.save(application);
        return application;
    }
}