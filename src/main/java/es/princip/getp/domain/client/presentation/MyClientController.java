package es.princip.getp.domain.client.presentation;

import es.princip.getp.domain.client.application.ClientService;
import es.princip.getp.domain.client.application.command.CreateClientCommand;
import es.princip.getp.domain.client.application.command.UpdateClientCommand;
import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.client.domain.ClientRepository;
import es.princip.getp.domain.client.presentation.dto.request.CreateClientRequest;
import es.princip.getp.domain.client.presentation.dto.request.UpdateClientRequest;
import es.princip.getp.domain.client.presentation.dto.response.ClientResponse;
import es.princip.getp.domain.member.domain.model.Member;
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

    private final ClientRepository clientRepository;
    private final ClientService clientService;

    /**
     * 내 의뢰자 정보 등록
     *
     * @param request 등록할 내 의뢰자 정보
     */
    @PostMapping
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ClientResponse>> create(
        @RequestBody @Valid CreateClientRequest request,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMember().getMemberId();
        CreateClientCommand command = request.toCommand(memberId);
        clientService.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED));
    }

    /**
     * 내 의뢰자 정보 조회
     * 
     * @return 내 의뢰자 정보
     */
    @GetMapping
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ClientResponse>> getMyClient(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        Client client = clientRepository.findByMemberId(member.getMemberId()).orElseThrow();
        ClientResponse response = ClientResponse.from(client, member);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 내 의뢰자 정보 수정
     * 
     * @param request 수정할 내 의뢰자 정보
     */
    @PutMapping
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> update(
            @RequestBody @Valid UpdateClientRequest request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMember().getMemberId();
        UpdateClientCommand command = request.toCommand(memberId);
        clientService.update(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED));
    }
}