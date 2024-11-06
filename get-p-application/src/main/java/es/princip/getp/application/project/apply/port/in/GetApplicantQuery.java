package es.princip.getp.application.project.apply.port.in;

import es.princip.getp.api.controller.project.query.dto.ProjectApplicantResponse;
import es.princip.getp.api.support.dto.SliceResponse;
import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.project.commission.model.ProjectId;

public interface GetApplicantQuery {

    SliceResponse<ProjectApplicantResponse> getApplicantsBy(CursorPageable<? extends Cursor> pageable, Member member, ProjectId projectId);
}
