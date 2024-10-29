package es.princip.getp.application.project.apply.port.in;

import es.princip.getp.api.controller.project.query.dto.ProjectApplicationDetailResponse;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;

public interface GetApplicationDetailQuery {

    ProjectApplicationDetailResponse getApplicationDetailBy(
        Member member,
        ProjectApplicationId applicationId
    );
}
