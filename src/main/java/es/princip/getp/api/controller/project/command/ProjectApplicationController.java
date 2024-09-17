package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.project.command.dto.request.ApplyProjectRequest;
import es.princip.getp.api.controller.project.command.dto.response.ApplyProjectResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.project.apply.command.ApplyProjectCommand;
import es.princip.getp.application.project.apply.port.in.ApplyProjectUseCase;
import es.princip.getp.domain.member.model.MemberId;
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
public class ProjectApplicationController {

    private final ApplyProjectUseCase applyProjectUseCase;
    private final ProjectCommandMapper projectCommandMapper;

    /**
     * 프로젝트 지원
     *
     * @param request          프로젝트 지원 요청
     * @param principalDetails 로그인한 사용자 정보
     * @param projectId        프로젝트 ID
     */
    @PostMapping("/{projectId}/applications")
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ApplyProjectResponse>> applyForProject(
        @RequestBody @Valid final ApplyProjectRequest request,
        @AuthenticationPrincipal final PrincipalDetails principalDetails,
        @PathVariable Long projectId
    ) {
        final MemberId memberId = principalDetails.getMember().getMemberId();
        final ApplyProjectCommand command = projectCommandMapper.mapToCommand(memberId, projectId, request);
        final Long applicationId = applyProjectUseCase.apply(command);
        final ApplyProjectResponse response = new ApplyProjectResponse(applicationId);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }
}
