package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.api.support.dto.PageResponse;
import es.princip.getp.api.controller.project.query.dto.CommissionedProjectCardResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.application.project.commission.port.in.GetCommissionedProjectQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client/me/projects")
@RequiredArgsConstructor
public class CommissionedProjectQueryController {

    private final GetCommissionedProjectQuery getCommissionedProjectQuery;

    /**
     * 의뢰한 프로젝트 목록 조회
     *
     * @param pageable 페이지 정보
     * @param principalDetails 로그인 정보
     * @return 의뢰한 프로젝트 목록
     */
    @GetMapping
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<PageResponse<CommissionedProjectCardResponse>>> getMyCommissionedProjects(
        @PageableDefault(sort = "projectId", direction = Sort.Direction.DESC) final Pageable pageable,
        @RequestParam(defaultValue = "false") final Boolean cancelled, // 만료된 프로젝트 보기 여부
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final Long memberId = principalDetails.getMember().getMemberId();
        final Page<CommissionedProjectCardResponse> page
            = getCommissionedProjectQuery.getPagedCards(memberId, cancelled, pageable);
        final PageResponse<CommissionedProjectCardResponse> response = PageResponse.from(page);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
