package es.princip.getp.application.project.apply.port.in;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;

public interface ApproveTeammateUseCase {

    void approve(ProjectApplicationId applicationId, MemberId teammateId);
}
