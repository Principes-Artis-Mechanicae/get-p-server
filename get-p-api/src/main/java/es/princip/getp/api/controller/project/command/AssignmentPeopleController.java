package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.project.command.dto.request.AssignmentPeopleRequest;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.auth.service.PrincipalDetails;
import es.princip.getp.application.project.assign.dto.command.AssignmentPeopleCommand;
import es.princip.getp.application.project.assign.dto.response.AssignmentPeopleResponse;
import es.princip.getp.application.project.assign.port.in.AssignmentPeopleUseCase;
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
@RequiredArgsConstructor
@RequestMapping("/projects")
public class AssignmentPeopleController {
    private final AssignmentPeopleUseCase confirmationProjectUseCase;
    private final ProjectCommandMapper projectCommandMapper;

    /**
     * 확정자 할당
     *
     * @param request          프로젝트 확정자 할당 요청
     * @param principalDetails 로그인한 사용자 정보
     * @param projectId        프로젝트 ID
     */
    @PostMapping("/{projectId}/assignment")
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<AssignmentPeopleResponse>> assignmentPeople(
        @RequestBody @Valid final AssignmentPeopleRequest request,
        @AuthenticationPrincipal final PrincipalDetails principalDetails,
        @PathVariable Long projectId
    ) {
        final MemberId memberId = principalDetails.getMember().getId();
        final ProjectId pid = new ProjectId(projectId);
        final AssignmentPeopleCommand command = projectCommandMapper.mapToCommand(memberId, pid, request);
        final Long confirmationId = confirmationProjectUseCase.assign(command).getValue();
        final AssignmentPeopleResponse response = new AssignmentPeopleResponse(confirmationId);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }
}