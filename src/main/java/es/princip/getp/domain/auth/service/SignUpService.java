package es.princip.getp.domain.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.auth.exception.SignUpErrorCode;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.service.MemberService;
import es.princip.getp.domain.serviceTerm.service.ServiceTermService;
import es.princip.getp.global.exception.BusinessLogicException;
import es.princip.getp.global.util.EmailUtil;
import es.princip.getp.global.util.PasswordUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignUpService {
    private final ServiceTermService serviceTermService;
    private final EmailVerificationService emailVerificationService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public void sendEmailVerificationCodeForSignUp(String email) {
        if (memberService.existsByEmail(email)) {
            throw new BusinessLogicException(SignUpErrorCode.DUPLICATED_EMAIL);
        }
        emailVerificationService.sendEmailVerificationCode(email);
    }

    private void validateSignUpRequest(SignUpRequest request) {
        String email = request.email();
        String password = request.password();
        if (!EmailUtil.isValidEmail(email))
            throw new BusinessLogicException(SignUpErrorCode.WRONG_EMAIL);
        if (!PasswordUtil.isValidPassword(password))
            throw new BusinessLogicException(SignUpErrorCode.WRONG_PASSWORD);
        if (memberService.existsByEmail(email))
            throw new BusinessLogicException(SignUpErrorCode.DUPLICATED_EMAIL);
        if (!emailVerificationService.isVerifiedEmail(email))
            throw new BusinessLogicException(SignUpErrorCode.NOT_VERIFIED_EMAIL);
        if (!serviceTermService.isAgreedAllRequiredServiceTerms(request.serviceTerms()))
            throw new BusinessLogicException(SignUpErrorCode.NOT_AGREED_REQUIRED_SERVICE_TERM);
    }

    @Transactional
    public Member signUp(SignUpRequest request) {
        validateSignUpRequest(request);
        return memberService.create(request, passwordEncoder);
    }
}
