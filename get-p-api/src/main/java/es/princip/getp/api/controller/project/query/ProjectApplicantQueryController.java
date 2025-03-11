package es.princip.getp.api.controller.project.query;

import es.princip.getp.application.auth.service.PrincipalDetails;
import es.princip.getp.api.support.CursorDefault;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.project.apply.dto.response.ProjectApplicantResponse;
import es.princip.getp.application.project.apply.port.in.GetApplicantQuery;
import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
import es.princip.getp.application.support.dto.SliceResponse;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.project.commission.model.ProjectId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectApplicantQueryController {

    private final GetApplicantQuery getApplicantQuery;

    @PreAuthorize("isAuthenticated() and hasRole('CLIENT')")
    @GetMapping("/{projectId}/applicants")
    public ResponseEntity<ApiSuccessResult<SliceResponse<ProjectApplicantResponse>>> getApplicants(
        @CursorDefault @PageableDefault final CursorPageable<Cursor> pageable,
        @AuthenticationPrincipal final PrincipalDetails principalDetails,
        @PathVariable final Long projectId
    ) {
        final Member member = principalDetails.getMember();
        final ProjectId pid = new ProjectId(projectId);
        final SliceResponse<ProjectApplicantResponse> response = getApplicantQuery.getApplicantsBy(pageable, member, pid);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
