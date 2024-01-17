package es.princip.getp.domain.auth.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import es.princip.getp.domain.auth.entity.EmailVerification;
import es.princip.getp.domain.auth.exception.EmailVerificationErrorCode;
import es.princip.getp.domain.auth.repository.EmailVerificationRepository;
import es.princip.getp.global.exception.BusinessLogicException;
import es.princip.getp.global.util.RandomUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailVerificationService {
    private static final int VERIFICATION_CODE_LENGTH = 4;

    private final EmailService emailService;
    private final EmailVerificationRepository emailVerificationRepository;

    public Optional<EmailVerification> getByEmail(String email) {
        return emailVerificationRepository.findById(email);
    }

    public void sendEmailVerificationCode(String email) {
        if (getByEmail(email).isPresent()) {
            throw new BusinessLogicException(EmailVerificationErrorCode.ALREADY_EMAIL_VERIFICATION_CODE_SENDED);
        }
        String verificationCode = RandomUtil.generateRandomCode(VERIFICATION_CODE_LENGTH);
        emailService.sendEmail(email, "GET-P 인증 번호", verificationCode);
        EmailVerification emailVerification = new EmailVerification(email, verificationCode);
        emailVerificationRepository.save(emailVerification);
    }

    public void verifyEmail(String email, String verificationCode) {
        Optional<EmailVerification> emailVerificationOptional = getByEmail(email);
        if (emailVerificationOptional.isEmpty()) {
            throw new BusinessLogicException(EmailVerificationErrorCode.INVALID_EMAIL_VERIFICATION);
        }
        EmailVerification emailVerification = emailVerificationOptional.get();
        if (emailVerification.isVerified()) {
            throw new BusinessLogicException(EmailVerificationErrorCode.ALREADY_VERIFIED_EMAIL);
        }
        boolean result = emailVerification.verify(verificationCode);
        if (!result) {
            throw new BusinessLogicException(EmailVerificationErrorCode.INCORRECT_EMAIL_VERIFICATION_CODE);
        } else {
            emailVerificationRepository.save(emailVerification);
        }
    }

    public boolean isVerifiedEmail(String email) {
        Optional<EmailVerification> verification = getByEmail(email);
        return verification.map(EmailVerification::isVerified).orElse(false);
    }
}
