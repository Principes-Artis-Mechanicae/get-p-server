package es.princip.getp.application.project.apply;

import es.princip.getp.application.project.apply.command.ApplyProjectCommand;
import es.princip.getp.application.project.apply.exception.AlreadyAppliedProjectException;
import es.princip.getp.application.project.apply.port.in.ApplyProjectUseCase;
import es.princip.getp.application.project.apply.port.out.CheckProjectApplicationPort;
import es.princip.getp.application.project.apply.port.out.SaveProjectApplicationPort;
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
class ProjectApplicationService implements ApplyProjectUseCase {

    private final LoadProjectPort loadProjectPort;
    private final CheckProjectApplicationPort checkProjectApplicationPort;
    private final SaveProjectApplicationPort saveProjectApplicationPort;
    private final PeopleRepository peopleRepository;

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
        final People applicant = peopleRepository.findByMemberId(command.memberId())
            .orElseThrow(NotFoundPeopleException::new);
        final Long applicantId = applicant.getPeopleId();
        final Long projectId = command.projectId();
        final Project project = loadProjectPort.loadBy(command.projectId());
        if (checkProjectApplicationPort.existsByApplicantIdAndProjectId(applicantId, projectId)) {
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
