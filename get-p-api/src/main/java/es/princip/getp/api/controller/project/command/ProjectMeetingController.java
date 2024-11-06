package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.project.command.dto.request.ScheduleMeetingRequest;
import es.princip.getp.application.project.meeting.dto.response.ScheduleMeetingResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.project.meeting.ProjectMeetingService;
import es.princip.getp.application.project.meeting.dto.command.ScheduleMeetingCommand;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;
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
        final MemberId memberId = principalDetails.getMember().getId();
        final ProjectId pid = new ProjectId(projectId);
        final ScheduleMeetingCommand command = projectCommandMapper.mapToCommand(memberId, pid, request);
        final Long meetingId = projectMeetingService.scheduleMeeting(command);
        final ScheduleMeetingResponse response = new ScheduleMeetingResponse(meetingId);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }
}
