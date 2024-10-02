package es.princip.getp.application.project.apply.port.in;

import es.princip.getp.api.controller.project.query.dto.SearchTeammateResponse;
import es.princip.getp.api.support.dto.SliceResponse;
import es.princip.getp.application.people.command.SearchTeammateCommand;

public interface SearchTeammateQuery {

    SliceResponse<SearchTeammateResponse> search(SearchTeammateCommand command);
}
