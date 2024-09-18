package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.controller.project.query.dto.AppliedProjectCardResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.api.support.dto.PageResponse;
import es.princip.getp.application.project.apply.port.in.GetAppliedProjectQuery;
import es.princip.getp.domain.member.model.MemberId;
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

    private final GetAppliedProjectQuery getAppliedProjectQuery;

    @GetMapping("/me/projects")
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<PageResponse<AppliedProjectCardResponse>>> getMyAppliedProjects(
        @PageableDefault(sort = "projectId", direction = Sort.Direction.DESC) final Pageable pageable,
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final MemberId memberId = principalDetails.getMember().getId();
        final Page<AppliedProjectCardResponse> page = getAppliedProjectQuery.getPagedCards(memberId, pageable);
        final PageResponse<AppliedProjectCardResponse> response = PageResponse.from(page);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
