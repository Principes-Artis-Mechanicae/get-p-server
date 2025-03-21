package es.princip.getp.api.controller.client.query;

import es.princip.getp.application.client.dto.response.ClientResponse;
import es.princip.getp.application.auth.service.PrincipalDetails;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.client.port.out.ClientQuery;
import es.princip.getp.domain.member.model.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client/me")
@RequiredArgsConstructor
public class MyClientQueryController {

    private final ClientQuery clientQuery;

    /**
     * 내 의뢰자 정보 조회
     * 
     * @return 내 의뢰자 정보
     */
    @GetMapping
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ClientResponse>> getMyClient(
            @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final MemberId memberId = principalDetails.getMember().getId();
        final ClientResponse response = clientQuery.findClientBy(memberId);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}