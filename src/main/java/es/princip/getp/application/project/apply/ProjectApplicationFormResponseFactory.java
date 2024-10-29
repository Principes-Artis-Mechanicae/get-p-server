package es.princip.getp.application.project.apply;

import es.princip.getp.api.controller.people.query.dto.peopleProfile.PortfolioResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectApplicationFormResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.application.member.port.out.LoadMemberPort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.TechStack;
import es.princip.getp.domain.common.model.URL;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.project.apply.model.IndividualProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.TeamProjectApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static es.princip.getp.domain.project.apply.model.ProjectApplicationType.INDIVIDUAL;
import static es.princip.getp.domain.project.apply.model.ProjectApplicationType.TEAM;

@Component
@RequiredArgsConstructor
class ProjectApplicationFormResponseFactory {

    private final LoadPeoplePort loadPeoplePort;
    private final LoadMemberPort loadMemberPort;
    private final ProjectApplicationDetailTeammateResponseFactory teammateResponseFactory;

    ProjectApplicationFormResponse mapToResponse(
        final ProjectApplication application,
        final ProjectDetailResponse project
    ) {
        if (application instanceof TeamProjectApplication teamApplication) {
            return mapToResponse(teamApplication, project);
        }
        if (application instanceof IndividualProjectApplication individualApplication) {
            return mapToResponse(individualApplication, project);
        }
        throw new IllegalArgumentException("지원서 타입이 올바르지 않습니다.");
    }

    private ProjectApplicationFormResponse mapToResponse(
        final TeamProjectApplication application,
        final ProjectDetailResponse project
    ) {
        final People applicant = loadPeoplePort.loadBy(application.getApplicantId());
        final Member member = loadMemberPort.loadBy(applicant.getMemberId());
        return new ProjectApplicationFormResponse(
            application.getId().getValue(),
            TEAM,
            project,
            member.getNickname().getValue(),
            applicant.getProfile().getTechStacks().stream()
                .map(TechStack::getValue)
                .toList(),
            applicant.getProfile().getActivityArea(),
            applicant.getProfile().getEducation(),
            applicant.getProfile().getIntroduction(),
            applicant.getProfile().getPortfolios().stream()
                .map(portfolio -> new PortfolioResponse(
                    portfolio.getDescription(),
                    portfolio.getUrl().getValue()
                ))
                .toList(),
            application.getExpectedDuration(),
            application.getStatus(),
            application.getDescription(),
            application.getAttachmentFiles().stream()
                .map(AttachmentFile::getUrl)
                .map(URL::getValue)
                .toList(),
            teammateResponseFactory.mapToResponse(application)
        );
    }

    private ProjectApplicationFormResponse mapToResponse(
        final IndividualProjectApplication application,
        final ProjectDetailResponse project
    ) {
        final People applicant = loadPeoplePort.loadBy(application.getApplicantId());
        final Member member = loadMemberPort.loadBy(applicant.getMemberId());
        return new ProjectApplicationFormResponse(
            application.getId().getValue(),
            INDIVIDUAL,
            project,
            member.getNickname().getValue(),
            applicant.getProfile().getTechStacks().stream()
                .map(TechStack::getValue)
                .toList(),
            applicant.getProfile().getActivityArea(),
            applicant.getProfile().getEducation(),
            applicant.getProfile().getIntroduction(),
            applicant.getProfile().getPortfolios().stream()
                .map(portfolio -> new PortfolioResponse(
                        portfolio.getDescription(),
                        portfolio.getUrl().getValue()
                ))
                .toList(),
            application.getExpectedDuration(),
            application.getStatus(),
            application.getDescription(),
            application.getAttachmentFiles().stream()
                .map(AttachmentFile::getUrl)
                .map(URL::getValue)
                .toList(),
            null
        );
    }
}
