package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.controller.ApiResponse;
import es.princip.getp.api.controller.ApiResponse.ApiSuccessResult;
import es.princip.getp.api.controller.PageResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.project.query.application.ProjectApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectApplicantQueryController {

    private final ProjectApplicantService projectApplicantService;

    /**
     * 프로젝트 지원자 목록 조회
     */
    @GetMapping("/{projectId}/applicants")
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<PageResponse<DetailPeopleResponse>>> getApplicantsByProjectId(
        @PathVariable final Long projectId,
        @PageableDefault(sort = "peopleId", direction = Sort.Direction.DESC) final Pageable pageable,
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final Member member = principalDetails.getMember();
        final PageResponse<DetailPeopleResponse> response =
            projectApplicantService.getApplicants(projectId, member, pageable);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
