package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.project.apply.port.in.ApproveTeammateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teammates")
public class ApproveTeammateController {

    private final ApproveTeammateUseCase approveTeammateUseCase;

    @GetMapping("/approve")
    public ResponseEntity<ApiSuccessResult<?>> approveTeammate(@RequestParam("token") final String token) {
        approveTeammateUseCase.approve(token);
        return ApiResponse.success(HttpStatus.OK);
    }
}
