package es.princip.getp.application.project.apply;

import es.princip.getp.application.project.apply.command.ApplyProjectCommand;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.people.exception.NotFoundPeopleException;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.service.ProjectApplier;
import es.princip.getp.domain.project.commission.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectApplicationService {

    private final LoadProjectPort loadProjectPort;
    private final PeopleRepository peopleRepository;
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
            .orElseThrow(NotFoundPeopleException::new);
        final Project project = loadProjectPort.loadBy(command.projectId());
        final ProjectApplication application = projectApplier.applyForProject(
            people,
            project,
            command.expectedDuration(),
            command.description(),
            command.attachmentFiles()
        );
        return application.getApplicationId();
    }
}
