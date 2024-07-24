package es.princip.getp.domain.project.query.presentation;

import es.princip.getp.domain.project.query.dao.MyProjectDao;
import es.princip.getp.domain.project.query.dto.MyProjectCardResponse;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.dto.response.PageResponse;
import es.princip.getp.infra.security.details.PrincipalDetails;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects/me")
@RequiredArgsConstructor
public class MyProjectQueryController {

    private final MyProjectDao myProjectDao;

    /**
     * 내 프로젝트 목록 조회
     *
     * @param pageable 페이지 정보
     * @param principalDetails 로그인 정보
     * @return 내 프로젝트 목록
     */
    @GetMapping
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<PageResponse<MyProjectCardResponse>>> getMyProjects(
        @PageableDefault(sort = "PROJECT_ID", direction = Sort.Direction.DESC) final Pageable pageable,
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final Long memberId = principalDetails.getMember().getMemberId();
        final Page<MyProjectCardResponse> page = myProjectDao.findPagedMyProjectCard(pageable, memberId);
        final PageResponse<MyProjectCardResponse> response = PageResponse.from(page);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
