package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.project.command.dto.request.CommissionProjectRequest;
import es.princip.getp.application.project.commission.dto.response.CommissionProjectResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.project.commission.dto.command.CommissionProjectCommand;
import es.princip.getp.application.project.commission.port.in.CommissionProjectUseCase;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectCommissionController {

    private final ProjectCommandMapper projectCommandMapper;
    private final CommissionProjectUseCase commissionProjectUseCase;

    /**
     * 프로젝트 의뢰
     *
     * @param request          프로젝트 의뢰 요청
     * @param principalDetails 로그인한 사용자 정보
     */
    @PostMapping
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<CommissionProjectResponse>> commissionProject(
        @RequestBody @Valid final CommissionProjectRequest request,
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final MemberId memberId = principalDetails.getMember().getId();
        final CommissionProjectCommand command = projectCommandMapper.mapToCommand(memberId, request);
        final ProjectId projectId = commissionProjectUseCase.commission(command);
        final CommissionProjectResponse response = new CommissionProjectResponse(projectId.getValue());
        return ApiResponse.success(HttpStatus.CREATED, response);
    }
}
