package es.princip.getp.persistence.adapter.project.meeting;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;

interface ProjectMeetingQueryDslRepository {

    boolean existsApplicantBy(ProjectId projectId, MemberId memberId);
}
