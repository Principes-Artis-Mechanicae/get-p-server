package es.princip.getp.domain.auth.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import es.princip.getp.domain.auth.entity.EmailVerification;
import es.princip.getp.domain.auth.exception.AlreadyVerificationCodeSendedException;
import es.princip.getp.domain.auth.exception.AlreadyVerifiedEmailException;
import es.princip.getp.domain.auth.exception.DuplicatedEmailException;
import es.princip.getp.domain.auth.exception.IncorrectVerificationCodeException;
import es.princip.getp.domain.auth.exception.InvalidVerificationException;
import es.princip.getp.domain.auth.repository.EmailVerificationRepository;
import es.princip.getp.domain.member.service.MemberService;
import es.princip.getp.global.util.RandomUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailVerificationService {
    private static final int VERIFICATION_CODE_LENGTH = 4;

    private final MemberService memberService;
    private final EmailService emailService;
    private final EmailVerificationRepository emailVerificationRepository;

    public Optional<EmailVerification> getByEmail(String email) {
        Optional<EmailVerification> emailVerification = emailVerificationRepository.findById(email);
        return emailVerification;
    }

    private void sendEmailVerificationCode(String email) {
        if (getByEmail(email).isPresent()) {
            throw new AlreadyVerificationCodeSendedException();
        }
        String verificationCode = RandomUtil.generateRandomCode(VERIFICATION_CODE_LENGTH);
        emailService.sendEmail(email, "GET-P 인증 번호", verificationCode);
        EmailVerification emailVerification = new EmailVerification(email, verificationCode);
        emailVerificationRepository.save(emailVerification);
    }

    public void sendEmailVerificationCodeForSignUp(String email) {
        if (memberService.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
        sendEmailVerificationCode(email);
    }

    public void verifyEmail(String email, String verificationCode) {
        Optional<EmailVerification> emailVerificationOptional = getByEmail(email);
        if (emailVerificationOptional.isEmpty()) {
            throw new InvalidVerificationException();
        }
        EmailVerification emailVerification = emailVerificationOptional.get();
        if (emailVerification.isVerified()) {
            throw new AlreadyVerifiedEmailException();
        }
        boolean result = emailVerification.verify(verificationCode);
        if (!result) {
            throw new IncorrectVerificationCodeException();
        } else {
            emailVerificationRepository.save(emailVerification);
        }
    }
}
