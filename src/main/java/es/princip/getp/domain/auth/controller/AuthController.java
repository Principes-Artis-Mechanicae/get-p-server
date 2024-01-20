package es.princip.getp.domain.auth.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.princip.getp.domain.auth.dto.request.LoginRequest;
import es.princip.getp.domain.auth.dto.response.Token;
import es.princip.getp.domain.auth.service.AuthService;
import es.princip.getp.global.util.ApiResponse;
import es.princip.getp.global.util.ApiResponse.ApiSuccessResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResult<Token>> login(@RequestBody @Valid LoginRequest loginRequest) {
        Token token = authService.login(loginRequest);
        String authorization = token.grantType() + " " + token.accessToken();
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .body(ApiResponse.success(HttpStatus.OK, token));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiSuccessResult<Token>> reissueAccessToken(HttpServletRequest servletRequest) {
        Token token = authService.reissueAccessToken(servletRequest);
        String authorization = token.grantType() + " " + token.accessToken();
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .body(ApiResponse.success(HttpStatus.OK, token));
    }
}
