package es.princip.getp.application.project.apply;

import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.project.apply.command.ApplyProjectCommand;
import es.princip.getp.application.project.apply.exception.AlreadyAppliedProjectException;
import es.princip.getp.application.project.apply.port.in.ApplyProjectUseCase;
import es.princip.getp.application.project.apply.port.out.CheckProjectApplicationPort;
import es.princip.getp.application.project.apply.port.out.SaveProjectApplicationPort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.service.ProjectApplier;
import es.princip.getp.domain.project.commission.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ProjectApplicationService implements ApplyProjectUseCase {

    private final LoadProjectPort loadProjectPort;
    private final CheckProjectApplicationPort checkProjectApplicationPort;
    private final SaveProjectApplicationPort saveProjectApplicationPort;
    private final LoadPeoplePort loadPeoplePort;

    private final ProjectApplier projectApplier;

    /**
     * 프로젝트 지원
     * 
     * @param command 프로젝트 지원 명령
     * @return 프로젝트 지원 ID
     */
    @Override
    @Transactional
    public Long apply(final ApplyProjectCommand command) {
        final People applicant = loadPeoplePort.loadBy(command.memberId());
        final Long applicantId = applicant.getId();
        final Long projectId = command.projectId();
        final Project project = loadProjectPort.loadBy(command.projectId());
        if (checkProjectApplicationPort.existsBy(applicantId, projectId)) {
            throw new AlreadyAppliedProjectException();
        }
        final ProjectApplication application = projectApplier.applyForProject(
            applicant,
            project,
            command.expectedDuration(),
            command.description(),
            command.attachmentFiles()
        );
        saveProjectApplicationPort.save(application);
        return application.getApplicationId();
    }
}
