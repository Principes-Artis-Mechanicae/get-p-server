package es.princip.getp.application.project.apply.port.out;

import es.princip.getp.application.project.apply.dto.response.SearchTeammateResponse;
import org.springframework.data.domain.Slice;

public interface SerializeTeammateCursorPort {

    String serializeCursor(Slice<SearchTeammateResponse> slice);
}
