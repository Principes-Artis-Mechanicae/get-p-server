package es.princip.getp.domain.client.command.presentation;

import es.princip.getp.domain.client.command.application.ClientService;
import es.princip.getp.domain.client.command.application.command.CreateClientCommand;
import es.princip.getp.domain.client.command.application.command.UpdateClientCommand;
import es.princip.getp.domain.client.command.presentation.dto.request.CreateClientRequest;
import es.princip.getp.domain.client.command.presentation.dto.request.UpdateClientRequest;
import es.princip.getp.domain.client.command.presentation.dto.response.CreateClientResponse;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.security.details.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client/me")
@RequiredArgsConstructor
public class MyClientController {

    private final ClientService clientService;

    /**
     * 내 의뢰자 정보 등록
     *
     * @param request 등록할 내 의뢰자 정보
     */
    @PostMapping
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<CreateClientResponse>> create(
        @RequestBody @Valid final CreateClientRequest request,
        @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final Long memberId = principalDetails.getMember().getMemberId();
        final CreateClientCommand command = request.toCommand(memberId);
        final Long clientId = clientService.create(command);
        final CreateClientResponse response = new CreateClientResponse(clientId);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }

    /**
     * 내 의뢰자 정보 수정
     * 
     * @param request 수정할 내 의뢰자 정보
     */
    @PutMapping
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> update(
            @RequestBody @Valid final UpdateClientRequest request,
            @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final Long memberId = principalDetails.getMember().getMemberId();
        final UpdateClientCommand command = request.toCommand(memberId);
        clientService.update(command);
        return ApiResponse.success(HttpStatus.OK);
    }
}