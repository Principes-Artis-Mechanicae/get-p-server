package es.princip.getp.domain.project.apply.service;

import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.people.exception.NotRegisteredPeopleProfileException;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.project.apply.exception.ClosedProjectApplicationException;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationData;
import es.princip.getp.domain.project.apply.model.TeamProjectApplication;
import es.princip.getp.domain.project.commission.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class TeamProjectApplier {

    private final TeamApprovalRequestSender teamApprovalRequestSender;

    public ProjectApplication apply(
        final Member applicantMember,
        final People applicant,
        final Project project,
        final ProjectApplicationData data,
        final Set<People> teammates
    ) {
        if (project.isApplicationClosed()) {
            throw new ClosedProjectApplicationException();
        }
        if (!applicant.isProfileRegistered()) {
            throw new NotRegisteredPeopleProfileException();
        }
        teammates.forEach(teammate -> {
            if (!teammate.isProfileRegistered()) {
                throw new NotRegisteredPeopleProfileException();
            }
        });
        teammates.forEach(teammate -> teamApprovalRequestSender.send(
            applicantMember,
            teammate,
            project,
            "승인 링크" // TODO: 승인 링크 생성
        ));
        return TeamProjectApplication.of(applicant.getId(), project.getId(), data, teammates);
    }
}
