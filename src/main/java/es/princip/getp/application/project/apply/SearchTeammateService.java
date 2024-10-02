package es.princip.getp.application.project.apply;

import es.princip.getp.api.controller.project.query.dto.SearchTeammateResponse;
import es.princip.getp.api.support.dto.SliceResponse;
import es.princip.getp.application.people.command.SearchTeammateCommand;
import es.princip.getp.application.project.apply.port.in.SearchTeammateQuery;
import es.princip.getp.application.project.apply.port.out.FindTeammatePort;
import es.princip.getp.application.project.apply.port.out.SerializeTeammateCursorPort;
import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
import es.princip.getp.domain.project.commission.model.ProjectId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchTeammateService implements SearchTeammateQuery {

    private final FindTeammatePort findTeammatePort;
    private final SerializeTeammateCursorPort serializeTeammateCursorPort;

    @Override
    public SliceResponse<SearchTeammateResponse> search(final SearchTeammateCommand command) {
        final ProjectId projectId = command.projectId();
        final CursorPageable<? extends Cursor> pageable = command.pageable();
        final String nickname = command.nickname();
        final Slice<SearchTeammateResponse> response = findTeammatePort.findBy(projectId, pageable, nickname);
        final String cursor = serializeTeammateCursorPort.serializeCursor(response);
        return SliceResponse.of(response, cursor);
    }
}