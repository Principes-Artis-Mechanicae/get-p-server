package es.princip.getp.domain.project.command.presentation;

import es.princip.getp.domain.project.command.application.ProjectApplicationService;
import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectRequest;
import es.princip.getp.domain.project.command.presentation.dto.response.ApplyProjectResponse;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.security.details.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectApplicationController {

    private final ProjectApplicationService projectApplicationService;

    /**
     * 프로젝트 지원
     *
     * @param request          프로젝트 지원 요청
     * @param principalDetails 로그인한 사용자 정보
     * @param projectId        프로젝트 ID
     */
    @PostMapping("/{projectId}/applications")
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ApplyProjectResponse>> applyForProject(
        @RequestBody @Valid final ApplyProjectRequest request,
        @AuthenticationPrincipal final PrincipalDetails principalDetails,
        @PathVariable Long projectId) {
        final Long memberId = principalDetails.getMember().getMemberId();
        final Long applicationId = projectApplicationService.applyForProject(memberId, projectId, request);
        final ApplyProjectResponse response = new ApplyProjectResponse(applicationId);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }
}
