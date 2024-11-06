package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.project.command.dto.request.ApplyProjectRequest;
import es.princip.getp.application.project.apply.dto.response.ApplyProjectResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.project.apply.dto.command.ApplyProjectCommand;
import es.princip.getp.application.project.apply.port.in.ApplyProjectUseCase;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
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
public class ProjectApplicationController {

    private final ApplyProjectUseCase applyProjectUseCase;
    private final ProjectCommandMapper projectCommandMapper;

    @PostMapping("/{projectId}/applications")
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ApplyProjectResponse>> applyProject(
        @RequestBody @Valid final ApplyProjectRequest request,
        @AuthenticationPrincipal final PrincipalDetails principalDetails,
        @PathVariable Long projectId
    ) {
        final Member member = principalDetails.getMember();
        final ProjectId pid = new ProjectId(projectId);
        final ApplyProjectCommand command = projectCommandMapper.mapToCommand(member, pid, request);
        final ProjectApplicationId applicationId = applyProjectUseCase.apply(command);
        final ApplyProjectResponse response = new ApplyProjectResponse(applicationId.getValue());
        return ApiResponse.success(HttpStatus.CREATED, response);
    }
}
