package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.support.ControllerSupport;
import es.princip.getp.api.support.CursorDefault;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.people.command.SearchTeammateCommand;
import es.princip.getp.application.project.apply.dto.response.SearchTeammateResponse;
import es.princip.getp.application.project.apply.port.in.SearchTeammateQuery;
import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
import es.princip.getp.application.support.dto.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class SearchTeammateController extends ControllerSupport {

    private final ProjectQueryCommandMapper mapper;
    private final SearchTeammateQuery searchTeammateQuery;

    @GetMapping("/{projectId}/teammates")
    public ResponseEntity<ApiSuccessResult<SliceResponse<SearchTeammateResponse>>> searchTeammates(
        @PathVariable final Long projectId,
        @CursorDefault @PageableDefault final CursorPageable<Cursor> pageable,
        @RequestParam(value = "nickname") final String nickname
    ) {
        final SearchTeammateCommand command = mapper.mapToCommand(projectId, pageable, nickname);
        final SliceResponse<SearchTeammateResponse> response = searchTeammateQuery.search(command);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}