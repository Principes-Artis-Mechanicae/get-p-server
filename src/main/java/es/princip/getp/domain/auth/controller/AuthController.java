package es.princip.getp.domain.auth.controller;

import es.princip.getp.domain.auth.dto.request.LoginRequest;
import es.princip.getp.domain.auth.dto.response.Token;
import es.princip.getp.domain.auth.service.AuthService;
import es.princip.getp.global.util.ApiResponse;
import es.princip.getp.global.util.ApiResponse.ApiSuccessResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<ApiSuccessResult<Token>> login(@RequestBody @Valid LoginRequest request) {
        Token token = authService.login(request);
        String authorization = token.grantType() + " " + token.accessToken();
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .body(ApiResponse.success(HttpStatus.CREATED, token));
    }

    /**
     * Access Token과 Refresh Token 재발급
     *
     * @param servletRequest HttpServletRequest
     * @return 재발급 된 Access Token과 Refresh Token
     */
    @PostMapping("/reissue")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<Token>> reissueAccessToken(HttpServletRequest servletRequest) {
        Token token = authService.reissueAccessToken(servletRequest);
        String authorization = token.grantType() + " " + token.accessToken();
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .body(ApiResponse.success(HttpStatus.CREATED, token));
    }
}
