package es.princip.getp.application.project.apply.port.out;

import es.princip.getp.api.controller.project.query.dto.SearchTeammateResponse;
import org.springframework.data.domain.Slice;

public interface SerializeTeammateCursorPort {

    String serializeCursor(Slice<SearchTeammateResponse> slice);
}
