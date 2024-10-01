package es.princip.getp.application.project.apply;

import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.project.apply.port.in.ApproveTeammateUseCase;
import es.princip.getp.application.project.apply.port.out.LoadProjectApplicantPort;
import es.princip.getp.application.project.apply.port.out.UpdateProjectApplicantPort;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import es.princip.getp.domain.project.apply.model.TeamProjectApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ApproveTeammateService implements ApproveTeammateUseCase {

    private final LoadPeoplePort loadPeoplePort;
    private final LoadProjectApplicantPort loadApplicationPort;
    private final UpdateProjectApplicantPort updateApplicationPort;

    @Transactional
    public void approve(final ProjectApplicationId applicationId, final MemberId teammateId) {
        final ProjectApplication application = loadApplicationPort.loadBy(applicationId);
        if (application instanceof TeamProjectApplication teamApplication) {
            final People teammate = loadPeoplePort.loadBy(teammateId);
            teamApplication.approve(teammate);
            updateApplicationPort.update(teamApplication);
            return ;
        }
        throw new IllegalArgumentException("팀 프로젝트 지원이 아닙니다.");
    }
}
