package es.princip.getp.application.project.apply;

import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.project.apply.command.ApplyProjectAsTeamCommand;
import es.princip.getp.application.project.apply.command.ApplyProjectCommand;
import es.princip.getp.application.project.apply.exception.AlreadyAppliedProjectException;
import es.princip.getp.application.project.apply.port.in.ApplyProjectUseCase;
import es.princip.getp.application.project.apply.port.out.CheckProjectApplicationPort;
import es.princip.getp.application.project.apply.port.out.SaveProjectApplicationPort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationData;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import es.princip.getp.domain.project.apply.service.IndividualProjectApplier;
import es.princip.getp.domain.project.apply.service.TeamProjectApplier;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ApplyProjectService implements ApplyProjectUseCase {

    private final LoadPeoplePort loadPeoplePort;
    private final LoadProjectPort loadProjectPort;
    private final CheckProjectApplicationPort checkApplicationPort;
    private final SaveProjectApplicationPort saveApplicationPort;

    private final ProjectApplicationDataMapper mapper;

    private final IndividualProjectApplier individualApplier;
    private final TeamProjectApplier teamApplier;

    @Override
    @Transactional
    public ProjectApplicationId apply(final ApplyProjectCommand command) {
        final People applicant = loadPeoplePort.loadBy(command.getMember().getId());
        final Project project = loadProjectPort.loadBy(command.getProjectId());
        validateApplicantAlreadyApplied(applicant, project);
        final ProjectApplication application = applyInternal(applicant, project, command);
        return saveApplicationPort.save(application);
    }

    private ProjectApplication applyInternal(
        final People applicant,
        final Project project,
        final ApplyProjectCommand command
    ) {
        final ProjectApplicationData data = mapper.mapToData(command);
        if (command instanceof ApplyProjectAsTeamCommand teamCommand) {
            final Member applicantMember = teamCommand.getMember();
            final Set<People> teammates = loadPeoplePort.loadBy(teamCommand.getTeammates());
            teammates.forEach(teammate -> validateApplicantAlreadyApplied(teammate, project));
            return teamApplier.apply(applicantMember, applicant, project, data, teammates);
        }
        return individualApplier.apply(applicant, project, data);
    }

    private void validateApplicantAlreadyApplied(final People applicant, final Project project) {
        final PeopleId applicantId = applicant.getId();
        final ProjectId projectId = project.getId();
        if (checkApplicationPort.existsBy(applicantId, projectId)) {
            throw new AlreadyAppliedProjectException();
        }
    }
}
