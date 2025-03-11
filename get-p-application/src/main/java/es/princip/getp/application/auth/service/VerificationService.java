package es.princip.getp.application.auth.service;

import es.princip.getp.application.auth.exception.IncorrectVerificationCodeException;
import es.princip.getp.domain.auth.EmailVerification;
import es.princip.getp.domain.common.model.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Service
@Transactional(readOnly = true)
public class VerificationService {

    private final VerificationCodeSender verificationSender;
    private final EmailVerificationRepository verificationRepository;

    private final Long EXPIRATION_TIME;
    private final Integer VERIFICATION_CODE_LENGTH;

    public VerificationService(
        final VerificationCodeSender verificationSender,
        final EmailVerificationRepository verificationRepository,
        @Value("${spring.verification-code.expire-time}") final Long EXPIRATION_TIME,
        @Value("${spring.verification-code.length}") final Integer VERIFICATION_CODE_LENGTH
    ) {
        this.verificationSender = verificationSender;
        this.verificationRepository = verificationRepository;
        this.EXPIRATION_TIME = EXPIRATION_TIME;
        this.VERIFICATION_CODE_LENGTH = VERIFICATION_CODE_LENGTH;
    }

    @Transactional
    public void sendVerificationCode(Email email) {
        verificationRepository.deleteById(email.getValue());
        final String verificationCode = generateRandomCode(VERIFICATION_CODE_LENGTH);
        verificationSender.send(email, verificationCode);
        final EmailVerification emailVerification = new EmailVerification(email.getValue(), verificationCode, EXPIRATION_TIME);
        verificationRepository.save(emailVerification);
    }

    @Transactional
    public void verifyEmail(final Email email, final String verificationCode) {
        final EmailVerification verification = verificationRepository.findById(email.getValue());
        if (!verification.verify(verificationCode)) {
            throw new IncorrectVerificationCodeException();
        }
        verificationRepository.deleteById(email.getValue());
    }

    private static String generateRandomCode(int length) {
        try {
            final Random random = SecureRandom.getInstanceStrong();
            final StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException exception) {
            throw new RuntimeException();
        }
    }
}
