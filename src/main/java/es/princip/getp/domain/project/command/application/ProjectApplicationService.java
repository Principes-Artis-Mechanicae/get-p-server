package es.princip.getp.domain.project.command.application;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleProfileRepository;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.project.command.domain.*;
import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectRequest;
import es.princip.getp.domain.project.exception.PeopleProfileNotRegisteredException;
import es.princip.getp.domain.project.exception.ProjectApplicationClosedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static es.princip.getp.domain.project.command.domain.ProjectApplicationStatus.APPLICATION_COMPLETED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectApplicationService {

    private final PeopleRepository peopleRepository;
    private final ProjectRepository projectRepository;
    private final PeopleProfileRepository peopleProfileRepository;
    private final ProjectApplicationRepository projectApplicationRepository;

    @Transactional
    public Long applyForProject(Long memberId, Long projectId, ApplyProjectRequest request) {
        final Project project = projectRepository.findById(projectId).orElseThrow();

        if (project.isApplicationClosed()) {
            throw new ProjectApplicationClosedException();
        }

        final People people = peopleRepository.findByMemberId(memberId).orElseThrow();

        // 피플 프로필을 등록한 경우에만 프로젝트에 지원할 수 있음
        if (!peopleProfileRepository.existsByPeopleId(people.getMemberId())) {
            throw new PeopleProfileNotRegisteredException();
        }

        final ExpectedDuration expectedDuration = ExpectedDuration.from(request.expectedDuration());
        final List<AttachmentFile> attachmentFiles = request.attachmentUris().stream()
            .map(AttachmentFile::from)
            .toList();

        final ProjectApplication application = ProjectApplication.builder()
            .applicantId(people.getPeopleId())
            .projectId(projectId)
            .expectedDuration(expectedDuration)
            .description(request.description())
            .attachmentFiles(attachmentFiles)
            .applicationStatus(APPLICATION_COMPLETED)
            .build();

        return projectApplicationRepository.save(application).getApplicationId();
    }
}
