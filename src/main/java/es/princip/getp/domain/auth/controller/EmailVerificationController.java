package es.princip.getp.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.princip.getp.domain.auth.dto.request.EmailVerificationCodeRequest;
import es.princip.getp.domain.auth.dto.request.EmailVerificationRequest;
import es.princip.getp.domain.auth.service.EmailVerificationService;
import es.princip.getp.global.util.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/email")
@RequiredArgsConstructor
public class EmailVerificationController {
    private final EmailVerificationService emailVerificationService;

    @PostMapping("/signup/send")
    public ResponseEntity<?> sendVerificationCodeForSignUp(@RequestBody EmailVerificationCodeRequest verificationCodeRequest) {
        String email = verificationCodeRequest.email();
        emailVerificationService.sendVerificationCodeForSignUp(email);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody EmailVerificationRequest verificationRequest) {
        String email = verificationRequest.email();
        String verificationCode = verificationRequest.code();
        emailVerificationService.verify(email, verificationCode);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK));
    }
}
