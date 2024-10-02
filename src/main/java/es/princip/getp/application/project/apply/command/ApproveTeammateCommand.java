package es.princip.getp.application.project.apply.command;

import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;

public record ApproveTeammateCommand(
    ProjectApplicationId applicationId,
    PeopleId teammateId
) {
}