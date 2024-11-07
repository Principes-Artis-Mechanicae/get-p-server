package es.princip.getp.application.people.dto.command;

import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
import es.princip.getp.domain.project.commission.model.ProjectId;

public record SearchTeammateCommand(
    ProjectId projectId,
    CursorPageable<? extends Cursor> pageable,
    String nickname
) {
}