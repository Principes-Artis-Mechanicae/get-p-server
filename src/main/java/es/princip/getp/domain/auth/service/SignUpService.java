package es.princip.getp.domain.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.auth.entity.EmailVerification;
import es.princip.getp.domain.auth.exception.SignUpErrorCode;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.service.MemberService;
import es.princip.getp.domain.serviceTermAgreement.service.ServiceTermAgreementService;
import es.princip.getp.global.exception.BusinessLogicException;
import es.princip.getp.global.util.EmailUtil;
import es.princip.getp.global.util.PasswordUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignUpService {
    private final ServiceTermAgreementService serviceTermAgreementService;
    private final EmailVerificationService emailVerificationService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public void sendEmailVerificationCodeForSignUp(String email) {
        if (memberService.existsByEmail(email)) {
            throw new BusinessLogicException(SignUpErrorCode.DUPLICATED_EMAIL);
        }
        emailVerificationService.sendEmailVerificationCode(email);
    }

    @Transactional
    public Member signUp(SignUpRequest signUpRequest) {
        String email = signUpRequest.email();
        String password = signUpRequest.password();
        if (!EmailUtil.isValidEmail(email)) throw new BusinessLogicException(SignUpErrorCode.WRONG_EMAIL);
        if (!PasswordUtil.isValidPassword(password)) throw new BusinessLogicException(SignUpErrorCode.WRONG_PASSWORD);
        EmailVerification verification = emailVerificationService
                .getByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(SignUpErrorCode.NOT_VERIFIED_EMAIL));
        if (!verification.isVerified()) throw new BusinessLogicException(SignUpErrorCode.NOT_VERIFIED_EMAIL);
        Member member = memberService.create(signUpRequest.toEntity(passwordEncoder));
        serviceTermAgreementService.agree(member.getMemberId(), signUpRequest.serviceTerms());
        return member;
    }
}
