package es.princip.getp.domain.auth.service;

import es.princip.getp.domain.auth.domain.entity.EmailVerification;
import es.princip.getp.domain.auth.exception.EmailVerificationErrorCode;
import es.princip.getp.domain.auth.repository.EmailVerificationRepository;
import es.princip.getp.global.exception.BusinessLogicException;
import es.princip.getp.global.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class EmailVerificationService {

    private static final int VERIFICATION_CODE_LENGTH = 4;

    private final EmailService emailService;
    private final EmailVerificationRepository emailVerificationRepository;

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
        EmailVerification emailVerification = new EmailVerification(email, verificationCode);
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
