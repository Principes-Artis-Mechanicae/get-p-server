package es.princip.getp.domain.auth.application;

import es.princip.getp.application.member.port.out.CheckMemberPort;
import es.princip.getp.application.member.port.out.SaveMemberPort;
import es.princip.getp.domain.auth.application.command.SignUpCommand;
import es.princip.getp.domain.auth.exception.DuplicatedEmailException;
import es.princip.getp.domain.member.model.Email;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.service.ServiceTermAgreementService;
import es.princip.getp.domain.member.exception.AlreadyUsedEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignUpService {

    private final VerificationService emailVerificationService;
    private final ServiceTermAgreementService agreementService;

    private final CheckMemberPort checkMemberPort;
    private final SaveMemberPort saveMemberPort;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void sendEmailVerificationCodeForSignUp(final Email email) {
        if (checkMemberPort.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
        emailVerificationService.sendVerificationCode(email);
    }

    @Transactional
    public void signUp(final SignUpCommand command) {
        emailVerificationService.verifyEmail(command.email(), command.verificationCode());
        if (checkMemberPort.existsByEmail(command.email()))
            throw new AlreadyUsedEmailException();
        final Member member = Member.of(command.email(), command.password(), command.memberType());
        agreementService.agreeServiceTerms(member, command.serviceTerms());
        member.encodePassword(passwordEncoder);
        saveMemberPort.save(member);
    }
}
