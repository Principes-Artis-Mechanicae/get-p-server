package es.princip.getp.application.project.apply.port.in;

import es.princip.getp.application.project.apply.dto.response.ProjectApplicationFormResponse;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;

public interface GetApplicationFormQuery {

    ProjectApplicationFormResponse getApplicationFormBy(
        Member member,
        ProjectApplicationId applicationId
    );
}
