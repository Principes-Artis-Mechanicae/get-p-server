package es.princip.getp.domain.project.command.application;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.project.command.application.command.ApplyProjectCommand;
import es.princip.getp.domain.project.command.domain.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectApplicationService {

    private final ProjectRepository projectRepository;
    private final PeopleRepository peopleRepository;
    private final ProjectApplicationRepository projectApplicationRepository;
    private final ProjectApplier projectApplier;

    /**
     * 프로젝트 지원
     * 
     * @param command 프로젝트 지원 명령
     * @return 프로젝트 지원 ID
     */
    @Transactional
    public Long applyForProject(final ApplyProjectCommand command) {
        final People people = peopleRepository.findByMemberId(command.memberId())
            .orElseThrow(() -> new EntityNotFoundException("해당 피플이 존재하지 않습니다."));
        final Project project = projectRepository.findById(command.projectId())
            .orElseThrow(() -> new EntityNotFoundException("해당 프로젝트가 존재하지 않습니다."));
        final ProjectApplication application = projectApplier.applyForProject(
            people,
            project,
            command.expectedDuration(),
            command.description(),
            command.attachmentFiles()
        );
        projectApplicationRepository.save(application);
        return application.getApplicationId();
    }
}
