package es.princip.getp.domain.client.query.presentation;

import es.princip.getp.domain.client.exception.ClientErrorCode;
import es.princip.getp.domain.client.query.dao.ClientDao;
import es.princip.getp.domain.client.query.dto.ClientResponse;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.exception.BusinessLogicException;
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
public class ClientQueryController {

    private final ClientDao clientDao;

    /**
     * 의뢰자 정보 조회
     * 
     * @param clientId 의뢰자 ID
     * @return 의뢰자 ID에 해당되는 의뢰자 정보
     */
    @GetMapping("/{clientId}")
    @PreAuthorize("(hasRole('ADMIN') or hasRole('MANAGER')) and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ClientResponse>> getClient(@PathVariable final Long clientId) {
        final ClientResponse response = clientDao.findById(clientId)
            .orElseThrow(() -> new BusinessLogicException(ClientErrorCode.NOT_FOUND));
        return ApiResponse.success(HttpStatus.OK, response);
    }
}