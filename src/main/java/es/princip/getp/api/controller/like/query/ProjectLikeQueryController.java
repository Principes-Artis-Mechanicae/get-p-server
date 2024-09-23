package es.princip.getp.api.controller.like.query;

import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.api.support.dto.PageResponse;
import es.princip.getp.application.like.project.port.in.GetLikedProjectQuery;
import es.princip.getp.domain.member.model.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectLikeQueryController {

    private final GetLikedProjectQuery getLikedProjectQuery;

    /**
     * 좋아요한 프로젝트 목록 조회
     */
    @GetMapping("/liked")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<PageResponse<ProjectCardResponse>>> getLikedProjects(
        @PageableDefault(sort = "projectId", direction = Sort.Direction.DESC) final Pageable pageable,
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final MemberId memberId = principalDetails.getMember().getId();
        final PageResponse<ProjectCardResponse> response = PageResponse.from(
            getLikedProjectQuery.getPagedCards(memberId, pageable)
        );
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
