package es.princip.getp.application.people.command;

import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;

public record SearchTeammateCommand(
    CursorPageable<Cursor> pageable,
    String nickname
) {
}