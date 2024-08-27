package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.controller.common.dto.ApiResponse;
import es.princip.getp.api.controller.common.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.api.controller.common.dto.PageResponse;
import es.princip.getp.api.controller.project.query.dto.AppliedProjectCardResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.domain.project.query.dao.AppliedProjectDao;
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
@RequestMapping("/people")
@RequiredArgsConstructor
public class AppliedProjectQueryController {

    private final AppliedProjectDao appliedProjectDao;

    @GetMapping("/me/projects")
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<PageResponse<AppliedProjectCardResponse>>> getMyAppliedProjects(
        @PageableDefault(sort = "projectId", direction = Sort.Direction.DESC) final Pageable pageable,
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final Long memberId = principalDetails.getMember().getMemberId();
        final Page<AppliedProjectCardResponse> page = appliedProjectDao.findPagedMyAppliedProjects(pageable, memberId);
        final PageResponse<AppliedProjectCardResponse> response = PageResponse.from(page);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
