package es.princip.getp.domain.auth.application;

import es.princip.getp.domain.auth.application.command.SignUpCommand;
import es.princip.getp.domain.auth.exception.DuplicatedEmailException;
import es.princip.getp.domain.member.command.application.MemberService;
import es.princip.getp.domain.member.command.application.command.CreateMemberCommand;
import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.member.command.domain.model.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignUpService {

    private final VerificationService emailVerificationService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void sendEmailVerificationCodeForSignUp(final Email email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
        emailVerificationService.sendVerificationCode(email);
    }

    @Transactional
    public void signUp(final SignUpCommand command) {
        emailVerificationService.verifyEmail(command.email(), command.verificationCode());
        final Long memberId = memberService.create(CreateMemberCommand.from(command));
        final Member member = memberRepository.findById(memberId).orElseThrow();
        member.encodePassword(passwordEncoder);
    }
}
