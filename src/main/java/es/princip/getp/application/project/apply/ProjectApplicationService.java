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
class ProjectApplicationService implements ApplyProjectUseCase {

    private final LoadPeoplePort loadPeoplePort;
    private final LoadProjectPort loadProjectPort;
    private final CheckProjectApplicationPort checkProjectApplicationPort;
    private final SaveProjectApplicationPort saveProjectApplicationPort;

    private final ProjectApplicationDataMapper mapper;

    private final TeamApprovalRequestSender teamApprovalRequestSender;
    private final TeamProjectApplier teamProjectApplier;
    private final IndividualProjectApplier individualProjectApplier;

    @Override
    @Transactional
    public ProjectApplicationId apply(final ApplyProjectCommand command) {
        final Member member = command.getMember();
        final People applicant = loadPeoplePort.loadBy(command.getMember().getId());
        final Project project = loadProjectPort.loadBy(command.getProjectId());
        validateApplicantAlreadyApplied(applicant, project);
        final ProjectApplicationData data = mapper.mapToData(command);
        if (command instanceof ApplyProjectAsTeamCommand teamCommand) {
            final Set<PeopleId> teammateIds = teamCommand.getTeammates();
            return applyAsTeam(member, applicant, project, data, teammateIds);
        }
        return applyAsIndividual(applicant, project, data);
    }

    private ProjectApplicationId applyAsIndividual(
        final People applicant,
        final Project project,
        final ProjectApplicationData data
    ) {
        final ProjectApplication application = individualProjectApplier.apply(applicant, project, data);
        return saveProjectApplicationPort.save(application);
    }

    private ProjectApplicationId applyAsTeam(
        final Member member,
        final People applicant,
        final Project project,
        final ProjectApplicationData data,
        final Set<PeopleId> teammateIds
    ) {
        final Set<People> teammates = loadPeoplePort.loadBy(teammateIds);
        teammates.forEach(teammate -> validateApplicantAlreadyApplied(teammate, project));
        teammates.forEach(teammate -> teamApprovalRequestSender.send(
            member,
            teammate,
            project,
            "승인 링크" // TODO: 승인 링크 생성
        ));
        final ProjectApplication application =  teamProjectApplier.apply(applicant, project, data, teammates);
        return saveProjectApplicationPort.save(application);
    }

    private void validateApplicantAlreadyApplied(final People applicant, final Project project) {
        final PeopleId applicantId = applicant.getId();
        final ProjectId projectId = project.getId();
        if (checkProjectApplicationPort.existsBy(applicantId, projectId)) {
            throw new AlreadyAppliedProjectException();
        }
    }
}
