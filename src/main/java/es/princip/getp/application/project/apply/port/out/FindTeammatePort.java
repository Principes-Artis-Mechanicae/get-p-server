package es.princip.getp.application.project.apply.port.out;

import es.princip.getp.api.controller.project.query.dto.SearchTeammateResponse;
import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
import org.springframework.data.domain.Slice;

public interface FindTeammatePort {

    Slice<SearchTeammateResponse> findBy(CursorPageable<Cursor> pageable, String nickname);
}
