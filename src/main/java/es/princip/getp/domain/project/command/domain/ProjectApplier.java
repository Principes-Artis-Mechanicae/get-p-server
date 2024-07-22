package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleProfileChecker;
import es.princip.getp.domain.project.exception.ProjectApplicationClosedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static es.princip.getp.domain.project.command.domain.ProjectApplicationStatus.APPLICATION_COMPLETED;

@Component
@RequiredArgsConstructor
public class ProjectApplier {

    private final PeopleProfileChecker peopleProfileChecker;

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
        final ExpectedDuration expectedDuration,
        final String description,
        final List<AttachmentFile> attachmentFiles
    ) {
        if (project.isApplicationClosed()) {
            throw new ProjectApplicationClosedException();
        }

        peopleProfileChecker.checkPeopleProfileIsRegistered(people);

        return ProjectApplication.builder()
            .applicantId(people.getPeopleId())
            .projectId(project.getProjectId())
            .expectedDuration(expectedDuration)
            .description(description)
            .attachmentFiles(attachmentFiles)
            .applicationStatus(APPLICATION_COMPLETED)
            .build();
    }
}
