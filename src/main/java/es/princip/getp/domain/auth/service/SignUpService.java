package es.princip.getp.domain.auth.service;

import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.auth.dto.response.SignUpResponse;
import es.princip.getp.domain.auth.exception.SignUpErrorCode;
import es.princip.getp.domain.member.dto.request.CreateMemberRequest;
import es.princip.getp.domain.member.service.MemberService;
import es.princip.getp.global.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignUpService {
    private final EmailVerificationService emailVerificationService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void sendEmailVerificationCodeForSignUp(String email) {
        if (memberService.existsByEmail(email)) {
            throw new BusinessLogicException(SignUpErrorCode.DUPLICATED_EMAIL);
        }
        emailVerificationService.sendEmailVerificationCode(email);
    }

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        emailVerificationService.verifyEmail(request.email(), request.verificationCode());
        return SignUpResponse.from(
            memberService.create(CreateMemberRequest.from(request, passwordEncoder))
        );
    }
}
