package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.controller.ApiResponse;
import es.princip.getp.api.controller.ApiResponse.ApiSuccessResult;
import es.princip.getp.api.controller.PageResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.controller.project.query.dto.MyCommissionedProjectCardResponse;
import es.princip.getp.domain.project.query.dao.MyCommissionedProjectDao;
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
public class MyCommissionedProjectQueryController {

    private final MyCommissionedProjectDao myCommissionedProjectDao;

    /**
     * 의뢰한 프로젝트 목록 조회
     *
     * @param pageable 페이지 정보
     * @param principalDetails 로그인 정보
     * @return 의뢰한 프로젝트 목록
     */
    @GetMapping
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<PageResponse<MyCommissionedProjectCardResponse>>> getMyCommissionedProjects(
        @PageableDefault(sort = "projectId", direction = Sort.Direction.DESC) final Pageable pageable,
        @RequestParam(defaultValue = "false") final Boolean cancelled, // 만료된 프로젝트 보기 여부
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final Long memberId = principalDetails.getMember().getMemberId();
        final Page<MyCommissionedProjectCardResponse> page = myCommissionedProjectDao.findPagedMyCommissionedProjectCard(pageable, memberId, cancelled);
        final PageResponse<MyCommissionedProjectCardResponse> response = PageResponse.from(page);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
