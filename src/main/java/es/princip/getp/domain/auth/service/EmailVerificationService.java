package es.princip.getp.domain.auth.service;

import es.princip.getp.domain.auth.domain.EmailVerification;
import es.princip.getp.domain.auth.exception.EmailVerificationErrorCode;
import es.princip.getp.domain.auth.repository.EmailVerificationRepository;
import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.util.RandomUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EmailVerificationService {

    private final EmailService emailService;
    private final EmailVerificationRepository emailVerificationRepository;

    private final Long EXPIRATION_TIME;
    private final Integer VERIFICATION_CODE_LENGTH;

    public EmailVerificationService(
        EmailService emailService,
        EmailVerificationRepository emailVerificationRepository,
        @Value("${spring.verification-code.expire-time}") Long EXPIRATION_TIME,
        @Value("${spring.verification-code.length}") Integer VERIFICATION_CODE_LENGTH
    ) {
        this.emailService = emailService;
        this.emailVerificationRepository = emailVerificationRepository;
        this.EXPIRATION_TIME = EXPIRATION_TIME;
        this.VERIFICATION_CODE_LENGTH = VERIFICATION_CODE_LENGTH;
    }

    public Optional<EmailVerification> getByEmail(String email) {
        return emailVerificationRepository.findById(email);
    }

    @Transactional
    public void sendEmailVerificationCode(String email) {
        if (getByEmail(email).isPresent()) {
            emailVerificationRepository.deleteById(email);
        }
        String verificationCode = RandomUtil.generateRandomCode(VERIFICATION_CODE_LENGTH);
        emailService.sendEmail(email, "GET-P 인증 번호", verificationCode);
        EmailVerification emailVerification = new EmailVerification(email, verificationCode, EXPIRATION_TIME);
        emailVerificationRepository.save(emailVerification);
    }

    @Transactional
    public void verifyEmail(String email, String verificationCode) {
        EmailVerification emailVerification = getByEmail(email).orElseThrow(() ->
            new BusinessLogicException(EmailVerificationErrorCode.INVALID_EMAIL_VERIFICATION));
        if (!emailVerification.verify(verificationCode)) {
            throw new BusinessLogicException(
                EmailVerificationErrorCode.INCORRECT_EMAIL_VERIFICATION_CODE);
        }
        emailVerificationRepository.delete(emailVerification);
    }
}
