package es.princip.getp.api.controller.like.command;

import es.princip.getp.application.auth.service.PrincipalDetails;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.like.project.port.in.LikeProjectUseCase;
import es.princip.getp.application.like.project.port.in.UnlikeProjectUseCase;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;
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

    private final LikeProjectUseCase likeProjectUseCase;
    private final UnlikeProjectUseCase unlikeProjectUseCase;

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
        final MemberId memberId = principalDetails.getMember().getId();
        final ProjectId pid = new ProjectId(projectId);
        likeProjectUseCase.like(memberId, pid);
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
        final MemberId memberId = principalDetails.getMember().getId();
        final ProjectId pid = new ProjectId(projectId);
        unlikeProjectUseCase.unlike(memberId, pid);
        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }
}
