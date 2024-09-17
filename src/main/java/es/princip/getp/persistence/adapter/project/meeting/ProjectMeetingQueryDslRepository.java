package es.princip.getp.persistence.adapter.project.meeting;

import es.princip.getp.domain.member.model.MemberId;

interface ProjectMeetingQueryDslRepository {

    boolean existsApplicantBy(Long projectId, MemberId memberId);
}
