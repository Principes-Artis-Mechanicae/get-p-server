package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.ApiResponse;
import es.princip.getp.api.controller.ApiResponse.ApiSuccessResult;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.application.projectMeeting.ProjectMeetingService;
import es.princip.getp.application.projectMeeting.command.ScheduleMeetingCommand;
import es.princip.getp.api.controller.project.command.dto.request.ScheduleMeetingRequest;
import es.princip.getp.api.controller.project.command.dto.response.ScheduleMeetingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
     */
    @PostMapping("/{projectId}/meetings")
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ScheduleMeetingResponse>> scheduleMeeting(
        @RequestBody @Valid final ScheduleMeetingRequest request,
        @AuthenticationPrincipal final PrincipalDetails principalDetails,
        @PathVariable Long projectId
    ) {
        final Long memberId = principalDetails.getMember().getMemberId();
        final ScheduleMeetingCommand command = projectCommandMapper.mapToCommand(memberId, projectId, request);
        final Long meetingId = projectMeetingService.scheduleMeeting(command);
        final ScheduleMeetingResponse response = new ScheduleMeetingResponse(meetingId);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }
}
