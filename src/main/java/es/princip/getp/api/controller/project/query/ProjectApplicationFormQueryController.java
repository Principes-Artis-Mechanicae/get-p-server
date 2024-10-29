package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.controller.project.query.dto.ProjectApplicationFormResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.project.apply.port.in.GetApplicationFormQuery;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ProjectApplicationFormQueryController {

    private final GetApplicationFormQuery query;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{applicationId}")
    public ResponseEntity<ApiSuccessResult<ProjectApplicationFormResponse>> getApplicationForm(
        @AuthenticationPrincipal final PrincipalDetails principalDetails,
        @PathVariable final Long applicationId
    ) {
        final Member member = principalDetails.getMember();
        final ProjectApplicationId aid = new ProjectApplicationId(applicationId);
        final ProjectApplicationFormResponse response = query.getApplicationFormBy(member, aid);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}
