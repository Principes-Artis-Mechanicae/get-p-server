package es.princip.getp.domain.project.command.presentation;

import es.princip.getp.domain.project.command.application.ProjectCommissionService;
import es.princip.getp.domain.project.command.application.command.CommissionProjectCommand;
import es.princip.getp.domain.project.command.presentation.dto.request.CommissionProjectRequest;
import es.princip.getp.domain.project.command.presentation.dto.response.CommissionProjectResponse;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.security.details.PrincipalDetails;
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
    private final ProjectCommissionService projectCommissionService;

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
        final Long memberId = principalDetails.getMember().getMemberId();
        final CommissionProjectCommand command = projectCommandMapper.mapToCommand(memberId, request);
        final Long projectId = projectCommissionService.commissionProject(command);
        final CommissionProjectResponse response = new CommissionProjectResponse(projectId);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }
}
