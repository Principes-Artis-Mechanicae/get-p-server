package es.princip.getp.domain.auth.presentation;

import es.princip.getp.domain.auth.application.SignUpService;
import es.princip.getp.domain.auth.application.command.SignUpCommand;
import es.princip.getp.domain.auth.presentation.dto.request.EmailVerificationCodeRequest;
import es.princip.getp.domain.auth.presentation.dto.request.SignUpRequest;
import es.princip.getp.domain.member.command.domain.model.Email;
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
     */
    @PostMapping()
    public ResponseEntity<ApiSuccessResult<?>> signUp(
        @RequestBody @Valid SignUpRequest request
    ) {
        SignUpCommand command = request.toCommand();
        signUpService.signUp(command);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED));
    }

    /**
     * 이메일 인증 코드 전송
     *
     * @param request 이메일 인증 코드 전송 요청
     */
    @PostMapping("/email/send")
    public ResponseEntity<ApiSuccessResult<?>> sendEmailVerificationCodeForSignUp(
        @RequestBody @Valid EmailVerificationCodeRequest request
    ) {
        Email email = Email.of(request.email());
        signUpService.sendEmailVerificationCodeForSignUp(email);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK));
    }
}
