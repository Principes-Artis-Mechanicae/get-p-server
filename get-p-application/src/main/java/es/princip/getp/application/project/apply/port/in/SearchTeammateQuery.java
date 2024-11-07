package es.princip.getp.application.project.apply.port.in;

import es.princip.getp.application.people.dto.command.SearchTeammateCommand;
import es.princip.getp.application.project.apply.dto.response.SearchTeammateResponse;
import es.princip.getp.application.support.dto.SliceResponse;

public interface SearchTeammateQuery {

    SliceResponse<SearchTeammateResponse> search(SearchTeammateCommand command);
}
