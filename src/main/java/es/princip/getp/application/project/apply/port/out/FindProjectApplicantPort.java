package es.princip.getp.application.project.apply.port.out;

import es.princip.getp.api.controller.project.query.dto.ProjectApplicantResponse;
import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.springframework.data.domain.Slice;

public interface FindProjectApplicantPort {

    Slice<ProjectApplicantResponse> findApplicantsBy(CursorPageable<? extends Cursor> pageable, ProjectId projectId);
}
