package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.project.command.dto.request.ConfirmProjectRequest;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.auth.service.PrincipalDetails;
import es.princip.getp.application.project.confirmation.dto.command.ConfirmationProjectCommand;
import es.princip.getp.application.project.confirmation.dto.response.ConfirmProjectResponse;
import es.princip.getp.application.project.confirmation.port.in.ConfirmationProjectUseCase;
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
public class ProjectConfirmationController {
    private final ConfirmationProjectUseCase confirmationProjectUseCase;
    private final ProjectCommandMapper projectCommandMapper;

    /**
     * 프로젝트 확정
     *
     * @param request          프로젝트 확정 요청
     * @param principalDetails 로그인한 사용자 정보
     * @param projectId        프로젝트 ID
     */
    @PostMapping("/{projectId}/confirmations")
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ConfirmProjectResponse>> confirmProject(
        @RequestBody @Valid final ConfirmProjectRequest request,
        @AuthenticationPrincipal final PrincipalDetails principalDetails,
        @PathVariable Long projectId
    ) {
        final MemberId memberId = principalDetails.getMember().getId();
        final ProjectId pid = new ProjectId(projectId);
        final ConfirmationProjectCommand command = projectCommandMapper.mapToCommand(memberId, pid, request);
        final Long confirmationId = confirmationProjectUseCase.confirm(command).getValue();
        final ConfirmProjectResponse response = new ConfirmProjectResponse(confirmationId);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }
}