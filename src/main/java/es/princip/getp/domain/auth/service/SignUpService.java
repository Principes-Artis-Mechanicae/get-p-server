package es.princip.getp.domain.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.auth.entity.EmailVerification;
import es.princip.getp.domain.auth.exception.InvalidVerificationException;
import es.princip.getp.domain.auth.exception.NotVerifiedEmailException;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.service.MemberService;
import es.princip.getp.domain.serviceTermAgreement.service.ServiceTermAgreementService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignUpService {
    private final ServiceTermAgreementService serviceTermAgreementService;
    private final EmailVerificationService emailVerificationService;
    private final MemberService memberService;

    @Transactional
    public Member signUp(SignUpRequest signUpRequest) {
        EmailVerification verification = emailVerificationService
                .getByEmail(signUpRequest.email())
                .orElseThrow(() -> new InvalidVerificationException());
        if (!verification.isVerified()) {
            throw new NotVerifiedEmailException();
        }
        // TODO: 비밀 번호 암호화
        Member member = memberService.create(signUpRequest);
        serviceTermAgreementService.agree(member.getMemberId(), signUpRequest.serviceTerms());
        return member;
    }
}
