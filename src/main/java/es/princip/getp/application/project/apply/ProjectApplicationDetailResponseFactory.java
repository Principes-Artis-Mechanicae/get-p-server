package es.princip.getp.application.project.apply;

import es.princip.getp.api.controller.project.query.dto.ProjectApplicationDetailResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectApplicationDetailTeammateResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.application.member.port.out.LoadMemberPort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.URL;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.IndividualProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.TeamProjectApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static es.princip.getp.domain.project.apply.model.ProjectApplicationType.INDIVIDUAL;
import static es.princip.getp.domain.project.apply.model.ProjectApplicationType.TEAM;

@Component
@RequiredArgsConstructor
class ProjectApplicationDetailResponseFactory {

    private final LoadPeoplePort loadPeoplePort;
    private final LoadMemberPort loadMemberPort;

    ProjectApplicationDetailResponse mapToResponse(
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

    private List<String> mapToStringList(final List<AttachmentFile> attachmentFiles) {
        return attachmentFiles.stream()
            .map(AttachmentFile::getUrl)
            .map(URL::getValue)
            .toList();
    }

    private List<ProjectApplicationDetailTeammateResponse> mapToTeammateResponses(
        final TeamProjectApplication application
    ) {
        return application.getTeammates().stream()
            .map(teammate -> {
                final PeopleId peopleId = teammate.getPeopleId();
                final People people = loadPeoplePort.loadBy(peopleId);
                final Member member = loadMemberPort.loadBy(people.getMemberId());
                return new ProjectApplicationDetailTeammateResponse(
                        teammate.getId().getValue(),
                        member.getNickname().getValue(),
                        teammate.getStatus(),
                        member.getProfileImage().getUrl()
                );
            })
            .toList();
    }

    private ProjectApplicationDetailResponse mapToResponse(
        final TeamProjectApplication application,
        final ProjectDetailResponse project
    ) {
        return new ProjectApplicationDetailResponse(
            application.getId().getValue(),
            TEAM,
            project,
            application.getExpectedDuration(),
            application.getStatus(),
            application.getDescription(),
            mapToStringList(application.getAttachmentFiles()),
            mapToTeammateResponses(application)
        );
    }

    private ProjectApplicationDetailResponse mapToResponse(
        final IndividualProjectApplication application,
        final ProjectDetailResponse project
    ) {
        return new ProjectApplicationDetailResponse(
            application.getId().getValue(),
            INDIVIDUAL,
            project,
            application.getExpectedDuration(),
            application.getStatus(),
            application.getDescription(),
            mapToStringList(application.getAttachmentFiles()),
            null
        );
    }
}
