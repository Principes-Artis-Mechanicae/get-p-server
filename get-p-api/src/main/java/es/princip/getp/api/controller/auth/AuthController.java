package es.princip.getp.api.controller.auth;

import es.princip.getp.api.controller.auth.dto.request.LoginRequest;
import es.princip.getp.api.controller.auth.dto.response.Token;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인
     *
     * @param request 로그인 요청 정보
     * @return 로그인 한 회원의 Access Token과 Refresh Token
     */
    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResult<Token>> login(@RequestBody @Valid final LoginRequest request) {
        final Token token = authService.login(request);
        final String authorization = token.grantType() + " " + token.accessToken();
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .body(ApiResponse.body(HttpStatus.CREATED, token));
    }

    /**
     * Access Token과 Refresh Token 재발급
     *
     * @param servletRequest HttpServletRequest
     * @return 재발급 된 Access Token과 Refresh Token
     */
    @PostMapping("/reissue")
    public ResponseEntity<ApiSuccessResult<Token>> reissueAccessToken(final HttpServletRequest servletRequest) {
        final Token token = authService.reissueAccessToken(servletRequest);
        final String authorization = token.grantType() + " " + token.accessToken();
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .body(ApiResponse.body(HttpStatus.CREATED, token));
    }
}
