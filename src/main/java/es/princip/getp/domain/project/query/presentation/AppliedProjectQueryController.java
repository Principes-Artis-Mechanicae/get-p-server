package es.princip.getp.domain.project.query.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.princip.getp.domain.project.query.dao.ProjectApplicationDao;
import es.princip.getp.domain.project.query.dto.AppliedProjectCardResponse;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.dto.response.PageResponse;
import es.princip.getp.infra.security.details.PrincipalDetails;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class AppliedProjectQueryController {

    private final ProjectApplicationDao projectApplicationDao;

    @GetMapping("/me/applications")
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<PageResponse<AppliedProjectCardResponse>>> getMyAppliedProjects(
        @PageableDefault(sort = "projectId", direction = Sort.Direction.DESC) final Pageable pageable,
        @RequestParam(defaultValue = "false") final Boolean cancelled, // 만료된 프로젝트 보기 여부
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final Long memberId = principalDetails.getMember().getMemberId();
        final Page<AppliedProjectCardResponse> page = projectApplicationDao.findPagedMyAppliedProjects(pageable, memberId, cancelled);
        final PageResponse<AppliedProjectCardResponse> response = PageResponse.from(page);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
