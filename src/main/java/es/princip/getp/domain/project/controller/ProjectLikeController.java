package es.princip.getp.domain.project.controller;

import es.princip.getp.domain.project.service.ProjectLikeService;
import es.princip.getp.global.security.details.PrincipalDetails;
import es.princip.getp.global.util.ApiResponse;
import es.princip.getp.global.util.ApiResponse.ApiSuccessResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectLikeController {

    private final ProjectLikeService projectLikeService;

    /**
     * 프로젝트 좋아요
     *
     * @param projectId        좋아요를 누를 프로젝트 ID
     * @param principalDetails 로그인한 사용자 정보
     */
    @PostMapping("/{projectId}/likes")
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> like(
        @PathVariable Long projectId,
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        projectLikeService.like(projectId, principalDetails.getMember().getMemberId());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED));
    }
}