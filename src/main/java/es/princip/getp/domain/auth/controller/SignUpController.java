package es.princip.getp.domain.auth.controller;

import es.princip.getp.domain.auth.dto.request.EmailVerificationCodeRequest;
import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.auth.dto.response.SignUpResponse;
import es.princip.getp.domain.auth.service.SignUpService;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/signup")
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    /**
     * 회원 가입
     *
     * @param request 회원 가입 요청
     * @return 가입된 회원 정보
     */
    @PostMapping()
    public ResponseEntity<ApiSuccessResult<SignUpResponse>> signUp(
        @RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED, signUpService.signUp(request)));
    }

    /**
     * 이메일 인증 코드 전송
     *
     * @param request 이메일 인증 코드 전송 요청
     */
    @PostMapping("/email/send")
    public ResponseEntity<ApiSuccessResult<?>> sendEmailVerificationCodeForSignUp(
        @RequestBody @Valid EmailVerificationCodeRequest request) {
        String email = request.email();
        signUpService.sendEmailVerificationCodeForSignUp(email);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK));
    }
}
