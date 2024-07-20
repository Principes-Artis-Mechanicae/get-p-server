package es.princip.getp.domain.client.query.presentation;

import es.princip.getp.domain.client.exception.ClientErrorCode;
import es.princip.getp.domain.client.query.dao.ClientDao;
import es.princip.getp.domain.client.query.dto.ClientResponse;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.security.details.PrincipalDetails;
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

    private final ClientDao clientDao;

    /**
     * 내 의뢰자 정보 조회
     * 
     * @return 내 의뢰자 정보
     */
    @GetMapping
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ClientResponse>> getMyClient(
            @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final Long memberId = principalDetails.getMember().getMemberId();
        final ClientResponse response = clientDao.findByMemberId(memberId)
            .orElseThrow(() -> new BusinessLogicException(ClientErrorCode.NOT_FOUND));
        return ApiResponse.success(HttpStatus.OK, response);
    }
}