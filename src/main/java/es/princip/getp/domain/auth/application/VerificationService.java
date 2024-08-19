package es.princip.getp.domain.auth.application;

import es.princip.getp.domain.auth.domain.EmailVerification;
import es.princip.getp.domain.auth.domain.EmailVerificationRepository;
import es.princip.getp.domain.auth.exception.IncorrectVerificationCodeException;
import es.princip.getp.domain.auth.exception.NotFoundVerificationException;
import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.infra.util.RandomUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        final String verificationCode = RandomUtil.generateRandomCode(VERIFICATION_CODE_LENGTH);
        verificationSender.send(email, verificationCode);
        final EmailVerification emailVerification = new EmailVerification(email.getValue(), verificationCode, EXPIRATION_TIME);
        verificationRepository.save(emailVerification);
    }

    @Transactional
    public void verifyEmail(final Email email, final String verificationCode) {
        final EmailVerification verification = verificationRepository.findById(email.getValue())
            .orElseThrow(NotFoundVerificationException::new);
        if (!verification.verify(verificationCode)) {
            throw new IncorrectVerificationCodeException();
        }
        verificationRepository.delete(verification);
    }
}
