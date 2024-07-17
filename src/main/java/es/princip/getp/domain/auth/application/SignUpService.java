package es.princip.getp.domain.auth.application;

import es.princip.getp.domain.auth.application.command.SignUpCommand;
import es.princip.getp.domain.auth.exception.SignUpErrorCode;
import es.princip.getp.domain.member.command.application.MemberService;
import es.princip.getp.domain.member.command.application.command.CreateMemberCommand;
import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.member.command.domain.model.MemberRepository;
import es.princip.getp.infra.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignUpService {

    private final EmailVerificationService emailVerificationService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void sendEmailVerificationCodeForSignUp(Email email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BusinessLogicException(SignUpErrorCode.DUPLICATED_EMAIL);
        }
        emailVerificationService.sendEmailVerificationCode(email);
    }

    @Transactional
    public void signUp(SignUpCommand command) {
        emailVerificationService.verifyEmail(command.email(), command.verificationCode());
        Long memberId = memberService.create(CreateMemberCommand.from(command));
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.encodePassword(passwordEncoder);
    }
}
