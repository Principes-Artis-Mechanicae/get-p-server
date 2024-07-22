package es.princip.getp.domain.project.command.presentation;

import es.princip.getp.domain.project.command.application.ProjectLikeService;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.security.details.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiSuccessResult<?>> likeProject(
        @PathVariable final Long projectId,
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final Long memberId = principalDetails.getMember().getMemberId();
        projectLikeService.like(projectId, memberId);
        return ApiResponse.success(HttpStatus.CREATED);
    }

    /**
     * 프로젝트 좋아요 취소
     *
     * @param projectId        좋아요를 취소할 프로젝트 ID
     * @param principalDetails 로그인한 사용자 정보
     */
    @DeleteMapping("/{projectId}/likes")
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> unlikeProject(
        @PathVariable final Long projectId,
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final Long memberId = principalDetails.getMember().getMemberId();
        projectLikeService.unlike(projectId, memberId);
        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }
}
