package es.princip.getp.domain.client.presentation;

import es.princip.getp.domain.client.dto.request.UpdateClientRequest;
import es.princip.getp.domain.client.dto.response.MyClientResponse;
import es.princip.getp.domain.client.service.ClientService;
import es.princip.getp.domain.member.domain.Member;
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
     * 내 의뢰자 정보 조회
     * 
     * @return 내 의뢰자 정보
     */
    @GetMapping
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<MyClientResponse>> getMyClient(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        MyClientResponse response = MyClientResponse.from(clientService.getByMemberId(member.getMemberId()));
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 내 의뢰자 정보 수정
     * 
     * @param request 수정할 내 의뢰자 정보
     * @return 수정된 내 의뢰자 정보
     */
    @PutMapping
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<MyClientResponse>> update(
            @RequestBody @Valid UpdateClientRequest request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        MyClientResponse response = MyClientResponse.from(clientService.update(member.getMemberId(), request));
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }
}