package es.princip.getp.application.project.apply;

import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.project.apply.command.ApplyProjectCommand;
import es.princip.getp.application.project.apply.exception.AlreadyAppliedProjectException;
import es.princip.getp.application.project.apply.port.in.ApplyProjectUseCase;
import es.princip.getp.application.project.apply.port.out.CheckProjectApplicationPort;
import es.princip.getp.application.project.apply.port.out.SaveProjectApplicationPort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import es.princip.getp.domain.project.apply.service.ProjectApplier;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
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

    @Override
    @Transactional
    public ProjectApplicationId apply(final ApplyProjectCommand command) {
        final People applicant = loadPeoplePort.loadBy(command.getMemberId());
        final Project project = loadProjectPort.loadBy(command.getProjectId());
        isApplicantAlreadyApplied(applicant, project);
        final ProjectApplication application = projectApplier.apply(
            applicant,
            project,
            command.getData()
        );
        return saveProjectApplicationPort.save(application);
    }

    private void isApplicantAlreadyApplied(final People applicant, final Project project) {
        final PeopleId applicantId = applicant.getId();
        final ProjectId projectId = project.getId();
        if (checkProjectApplicationPort.existsBy(applicantId, projectId)) {
            throw new AlreadyAppliedProjectException();
        }
    }
}
