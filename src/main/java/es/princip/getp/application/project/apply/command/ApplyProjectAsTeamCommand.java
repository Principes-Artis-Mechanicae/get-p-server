package es.princip.getp.application.project.apply.command;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.TeamProjectApplicationData;
import es.princip.getp.domain.project.commission.model.ProjectId;
import lombok.Getter;

import java.util.List;

@Getter
public class ApplyProjectAsTeamCommand extends ApplyProjectCommand {

    private final List<PeopleId> teams;

    public ApplyProjectAsTeamCommand(
        final MemberId memberId,
        final ProjectId projectId,
        final TeamProjectApplicationData data,
        final List<PeopleId> teams
    ) {
        super(memberId, projectId, data);
        this.teams = teams;
    }
}