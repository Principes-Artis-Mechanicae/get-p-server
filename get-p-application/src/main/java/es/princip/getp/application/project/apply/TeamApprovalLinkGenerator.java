package es.princip.getp.application.project.apply;

import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;

interface TeamApprovalLinkGenerator {

    String generate(ProjectApplicationId applicationId, PeopleId teammateId);
}
