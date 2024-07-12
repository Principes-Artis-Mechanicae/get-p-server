package es.princip.getp.domain.project.application;

import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleRepository;
import es.princip.getp.domain.project.domain.AttachmentFile;
import es.princip.getp.domain.project.domain.Project;
import es.princip.getp.domain.project.domain.ProjectApplication;
import es.princip.getp.domain.project.domain.ProjectRepository;
import es.princip.getp.domain.project.dto.request.ApplyProjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static es.princip.getp.domain.project.domain.ProjectApplicationStatus.APPLICATION_COMPLETED;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectApplicationService {

    private final PeopleRepository peopleRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public void apply(Long memberId, Long projectId, ApplyProjectRequest request) {
        People people = peopleRepository.findByMemberId(memberId).orElseThrow();
        Project project = projectRepository.findById(projectId).orElseThrow();
        List<AttachmentFile> attachmentFiles = request.attachmentUris().stream()
            .map(AttachmentFile::from)
            .toList();
        ProjectApplication application = ProjectApplication.builder()
            .applicant(people.getPeopleId())
            .expectedDuration(request.expectedDuration())
            .description(request.description())
            .attachmentFiles(attachmentFiles)
            .applicationStatus(APPLICATION_COMPLETED)
            .build();
        project.apply(application);
    }
}
