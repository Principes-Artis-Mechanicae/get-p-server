package es.princip.getp.domain.client.presentation;

import es.princip.getp.domain.client.application.ClientService;
import es.princip.getp.domain.client.dto.request.CreateClientRequest;
import es.princip.getp.domain.client.dto.response.ClientResponse;
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
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    /**
     * 의뢰자 정보 등록
     * 
     * @param request 등록할 의뢰자 정보
     * @return 등록 완료된 의뢰자 정보
     */
    @PostMapping
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ClientResponse>> create(
            @RequestBody @Valid CreateClientRequest request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMember().getMemberId();
        ClientResponse response = ClientResponse.from(clientService.create(memberId, request));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED, response));
    }

    /**
     * 의뢰자 정보 조회
     * 
     * @param clientId 의뢰자 ID
     * @return 의뢰자 ID에 해당되는 의뢰자 정보
     */
    @GetMapping("/{clientId}")
    @PreAuthorize("(hasRole('ADMIN') or hasRole('MANAGER')) and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ClientResponse>> getClient(@PathVariable Long clientId) {
        ClientResponse response = ClientResponse.from(clientService.getByMemberId(clientId));
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }
}