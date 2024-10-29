package es.princip.getp.application.project.apply;

import es.princip.getp.api.controller.project.query.dto.ProjectApplicationDetailTeammateResponse;
import es.princip.getp.application.member.port.out.LoadMemberPort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.TeamProjectApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class ProjectApplicationDetailTeammateResponseFactory {

    private final LoadPeoplePort loadPeoplePort;
    private final LoadMemberPort loadMemberPort;

    List<ProjectApplicationDetailTeammateResponse> mapToResponse(
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
}
