package es.princip.getp.domain.project.presentation;

import es.princip.getp.domain.project.application.ProjectApplicationService;
import es.princip.getp.domain.project.dto.request.ApplyProjectRequest;
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
    public ResponseEntity<ApiSuccessResult<?>> applyProject(
        @RequestBody @Valid ApplyProjectRequest request,
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long projectId) {
        Long memberId = principalDetails.getMember().getMemberId();
        projectApplicationService.apply(memberId, projectId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED));
    }
}
