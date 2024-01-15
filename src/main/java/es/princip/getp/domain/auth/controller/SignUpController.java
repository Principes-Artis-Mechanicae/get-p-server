package es.princip.getp.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.princip.getp.domain.auth.dto.request.EmailVerificationCodeRequest;
import es.princip.getp.domain.auth.dto.request.EmailVerificationRequest;
import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.auth.dto.response.SignUpResponse;
import es.princip.getp.domain.auth.service.EmailVerificationService;
import es.princip.getp.domain.auth.service.SignUpService;
import es.princip.getp.global.util.ApiResponse;
import es.princip.getp.global.util.ApiResponse.ApiSuccessResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/signup")
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpService signUpService;
    private final EmailVerificationService emailVerificationService;

    @PostMapping()
    public ResponseEntity<ApiSuccessResult<SignUpResponse>> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = SignUpResponse.from(signUpService.signUp(signUpRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED, signUpResponse));
    }

    @PostMapping("/email/send")
    public ResponseEntity<ApiSuccessResult<?>> sendEmailVerificationCodeForSignUp(@RequestBody @Valid EmailVerificationCodeRequest verificationCodeRequest) {
        String email = verificationCodeRequest.email();
        signUpService.sendEmailVerificationCodeForSignUp(email);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK));
    }

    @PostMapping("/email/verify")
    public ResponseEntity<ApiSuccessResult<?>> verifyEmail(@RequestBody @Valid EmailVerificationRequest verificationRequest) {
        String email = verificationRequest.email();
        String verificationCode = verificationRequest.code();
        emailVerificationService.verifyEmail(email, verificationCode);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK));
    }
}
