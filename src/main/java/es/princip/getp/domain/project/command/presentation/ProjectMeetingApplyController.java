package es.princip.getp.domain.project.command.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.princip.getp.domain.project.command.application.ProjectMeetingApplyService;
import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectMeetingRequest;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.security.details.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectMeetingApplyController {

    private final ProjectMeetingApplyService projectMeetingApplyService;

    /**
     * 프로젝트 미팅 신청
     *
     * @param request          프로젝트 미팅 신청 요청
     * @param principalDetails 로그인한 사용자 정보
     * @param peopleId         프로젝트 ID
     */
    @PostMapping("/meeting/{applicationId}/apply")
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> applyForProjectMeeting(
        @RequestBody @Valid final ApplyProjectMeetingRequest request,
        @AuthenticationPrincipal final PrincipalDetails principalDetails,
        @PathVariable Long applicationId) {
        final Long memberId = principalDetails.getMember().getMemberId();
        
        projectMeetingApplyService.applyForProjectMeeting(memberId, applicationId, request);
        return ApiResponse.success(HttpStatus.OK);
    }
}

