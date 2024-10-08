package es.princip.getp.api.controller.auth;

import es.princip.getp.api.controller.auth.dto.request.EmailVerificationCodeRequest;
import es.princip.getp.api.controller.auth.dto.request.SignUpRequest;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.auth.service.SignUpService;
import es.princip.getp.domain.common.model.Email;
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

    private final SignUpCommandMapper mapper;
    private final SignUpService signUpService;

    /**
     * 회원 가입
     *
     * @param request 회원 가입 요청
     */
    @PostMapping()
    public ResponseEntity<ApiSuccessResult<?>> signUp(
        @RequestBody @Valid final SignUpRequest request
    ) {
        signUpService.signUp(mapper.mapToCommand(request));
        return ApiResponse.success(HttpStatus.CREATED);
    }

    /**
     * 이메일 인증 코드 전송
     *
     * @param request 이메일 인증 코드 전송 요청
     */
    @PostMapping("/email/send")
    public ResponseEntity<ApiSuccessResult<?>> sendEmailVerificationCodeForSignUp(
        @RequestBody @Valid final EmailVerificationCodeRequest request
    ) {
        final Email email = Email.from(request.email());
        signUpService.sendEmailVerificationCodeForSignUp(email);
        return ApiResponse.success(HttpStatus.OK);
    }
}
