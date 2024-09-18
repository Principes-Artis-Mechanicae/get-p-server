package es.princip.getp.application.auth.service;

import es.princip.getp.application.auth.command.SignUpCommand;
import es.princip.getp.application.auth.exception.DuplicatedEmailException;
import es.princip.getp.application.member.port.out.CheckMemberPort;
import es.princip.getp.application.member.port.out.SaveMemberPort;
import es.princip.getp.application.serviceTerm.port.out.CheckServiceTermPort;
import es.princip.getp.application.serviceTerm.port.out.LoadServiceTermPort;
import es.princip.getp.domain.common.model.Email;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.ServiceTermAgreementData;
import es.princip.getp.domain.serviceTerm.model.ServiceTerm;
import es.princip.getp.domain.serviceTerm.model.ServiceTermTag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignUpService {

    private final VerificationService verificationService;

    private final LoadServiceTermPort loadServiceTermPort;
    private final CheckServiceTermPort checkServiceTermPort;
    private final CheckMemberPort checkMemberPort;
    private final SaveMemberPort saveMemberPort;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void sendEmailVerificationCodeForSignUp(final Email email) {
        if (checkMemberPort.existsBy(email)) {
            throw new DuplicatedEmailException();
        }
        verificationService.sendVerificationCode(email);
    }

    @Transactional
    public void signUp(final SignUpCommand command) {
        verificationService.verifyEmail(command.email(), command.verificationCode());
        if (checkMemberPort.existsBy(command.email())) {
            throw new DuplicatedEmailException();
        }
        final Member member = Member.of(command.email(), command.password(), command.memberType());
        member.encodePassword(passwordEncoder);
        final Set<ServiceTermTag> agreedTags = command.serviceTerms()
            .stream()
            .map(ServiceTermAgreementData::tag)
            .collect(Collectors.toSet());
        checkServiceTermPort.existsBy(agreedTags);
        final Set<ServiceTermTag> requiredTags = loadServiceTermPort.loadAllBy(true)
            .stream()
            .map(ServiceTerm::getTag)
            .collect(Collectors.toSet());
        member.agreeServiceTerms(requiredTags, command.serviceTerms());
        saveMemberPort.save(member);
    }
}
