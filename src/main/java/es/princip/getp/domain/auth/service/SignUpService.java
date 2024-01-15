package es.princip.getp.domain.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.auth.entity.EmailVerification;
import es.princip.getp.domain.auth.exception.InvalidVerificationException;
import es.princip.getp.domain.auth.exception.NotVerifiedEmailException;
import es.princip.getp.domain.auth.exception.WrongEmailException;
import es.princip.getp.domain.auth.exception.WrongPasswordException;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.service.MemberService;
import es.princip.getp.domain.serviceTermAgreement.service.ServiceTermAgreementService;
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

    @Transactional
    public Member signUp(SignUpRequest signUpRequest) {
        String email = signUpRequest.email();
        String password = signUpRequest.password();
        if (!EmailUtil.isValidEmail(email)) throw new WrongEmailException();
        if (!PasswordUtil.isValidPassword(password)) throw new WrongPasswordException();
        EmailVerification verification = emailVerificationService
                .getByEmail(email)
                .orElseThrow(() -> new InvalidVerificationException());
        if (!verification.isVerified()) throw new NotVerifiedEmailException();
        Member member = memberService.create(signUpRequest.toEntity(passwordEncoder));
        serviceTermAgreementService.agree(member.getMemberId(), signUpRequest.serviceTerms());
        return member;
    }
}
