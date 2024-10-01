package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.project.apply.port.in.ApproveTeammateUseCase;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApproveTeammateController {

    private final ApproveTeammateUseCase approveTeammateUseCase;

    @PostMapping("/{applicationId}/approve")
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> approveTeammate(
        @AuthenticationPrincipal final PrincipalDetails principalDetails,
        @PathVariable final Long applicationId
    ) {
        final MemberId memberId = principalDetails.getMember().getId();
        final ProjectApplicationId projectApplicationId = new ProjectApplicationId(applicationId);
        approveTeammateUseCase.approve(projectApplicationId, memberId);
        return ApiResponse.success(HttpStatus.OK);
    }
}
