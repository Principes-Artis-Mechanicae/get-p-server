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

import es.princip.getp.domain.project.command.application.ProjectMeetingService;
import es.princip.getp.domain.project.command.application.command.ScheduleMeetingCommand;
import es.princip.getp.domain.project.command.presentation.dto.request.ScheduleMeetingRequest;
import es.princip.getp.domain.project.command.presentation.dto.response.ScheduleMeetingResponse;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.security.details.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectMeetingController {

    private final ProjectMeetingService projectMeetingService;

    private final ProjectCommandMapper projectCommandMapper;

    /**
     * 프로젝트 미팅 신청
     * 
     * @param request          프로젝트 미팅 신청 요청
     * @param principalDetails 로그인한 사용자 정보
     * @param projectId        프로젝트 ID
     * @return
     */
    @PostMapping("/{projectId}/meetings")
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ScheduleMeetingResponse>> scheduleMeeting(
        @RequestBody @Valid final ScheduleMeetingRequest request,
        @AuthenticationPrincipal final PrincipalDetails principalDetails,
        @PathVariable Long projectId
    ) {
        final Long memberId = principalDetails.getMember().getMemberId();
        ScheduleMeetingCommand command = projectCommandMapper.mapToCommand(memberId, projectId, request);
        Long meetingId = projectMeetingService.ScheduleMeeting(command);
        ScheduleMeetingResponse response = new ScheduleMeetingResponse(meetingId);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }
}
