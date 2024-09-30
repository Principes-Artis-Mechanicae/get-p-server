package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.controller.project.query.dto.SearchTeammateResponse;
import es.princip.getp.api.support.ControllerSupport;
import es.princip.getp.api.support.CursorDefault;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.api.support.dto.SliceResponse;
import es.princip.getp.application.people.command.SearchTeammateCommand;
import es.princip.getp.application.project.apply.port.in.SearchTeammateQuery;
import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class SearchTeammateController extends ControllerSupport {

    private final SearchTeammateQuery searchTeammateQuery;

    @GetMapping("/teammates")
    public ResponseEntity<ApiSuccessResult<SliceResponse<SearchTeammateResponse>>> searchTeammates(
        @CursorDefault @PageableDefault final CursorPageable<Cursor> pageable,
        @RequestParam(value = "nickname") final String nickname
    ) {
        final SearchTeammateCommand command = new SearchTeammateCommand(pageable, nickname);
        final SliceResponse<SearchTeammateResponse> response = searchTeammateQuery.search(command);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}