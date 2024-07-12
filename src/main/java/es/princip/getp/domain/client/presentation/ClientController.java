package es.princip.getp.domain.client.presentation;

import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.client.domain.ClientRepository;
import es.princip.getp.domain.client.presentation.dto.response.ClientResponse;
import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.member.domain.model.MemberRepository;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepository clientRepository;
    private final MemberRepository memberRepository;


    /**
     * 의뢰자 정보 조회
     * 
     * @param clientId 의뢰자 ID
     * @return 의뢰자 ID에 해당되는 의뢰자 정보
     */
    @GetMapping("/{clientId}")
    @PreAuthorize("(hasRole('ADMIN') or hasRole('MANAGER')) and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ClientResponse>> getClient(@PathVariable Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        Member member = memberRepository.findById(client.getMemberId()).orElseThrow();
        ClientResponse response = ClientResponse.from(client, member);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }
}