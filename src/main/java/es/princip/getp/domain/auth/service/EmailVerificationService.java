package es.princip.getp.domain.auth.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import es.princip.getp.domain.auth.entity.EmailVerification;
import es.princip.getp.domain.auth.exception.EmailVerificationExceptionCode;
import es.princip.getp.domain.auth.repository.EmailVerificationRepository;
import es.princip.getp.domain.member.service.MemberService;
import es.princip.getp.global.exception.BusinessLogicException;
import es.princip.getp.global.util.RandomUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailVerificationService {
    private static final int VERIFICATION_CODE_LENGTH = 4;

    private final MemberService memberService;
    private final EmailService emailService;
    private final EmailVerificationRepository emailVerificationRepository;

    private Optional<EmailVerification> getEmailVerificationByEmail(String email) {
        Optional<EmailVerification> emailVerification = emailVerificationRepository.findById(email);
        return emailVerification;
    }

    private void sendVerificationCode(String email) {
        if (getEmailVerificationByEmail(email).isPresent()) {
            throw new BusinessLogicException(EmailVerificationExceptionCode.ALREADY_VERIFICATION_CODE_SENDED);
        }
        String verificationCode = RandomUtil.generateRandomCode(VERIFICATION_CODE_LENGTH);
        emailService.sendEmail(email, "GET-P 인증 번호", verificationCode);
        EmailVerification emailVerification = new EmailVerification(email, verificationCode);
        emailVerificationRepository.save(emailVerification);
    }

    public void sendVerificationCodeForSignUp(String email) {
        if (memberService.existsByEmail(email)) {
            throw new BusinessLogicException(EmailVerificationExceptionCode.DUPLICATED_EMAIL);
        }
        sendVerificationCode(email);
    }

    public void verify(String email, String verificationCode) {
        Optional<EmailVerification> emailVerificationOptional = getEmailVerificationByEmail(email);
        if (emailVerificationOptional.isEmpty()) {
            throw new BusinessLogicException(EmailVerificationExceptionCode.INVALID_VERIFICATION);
        }
        EmailVerification emailVerification = emailVerificationOptional.get();
        if (emailVerification.isVerified()) {
            throw new BusinessLogicException(EmailVerificationExceptionCode.AREADY_AUTHENTICATED_EMAIL);
        }
        boolean result = emailVerification.verify(verificationCode);
        if (!result) {
            throw new BusinessLogicException(EmailVerificationExceptionCode.INCORRECT_VERIFICATION_CODE);
        } else {
            emailVerificationRepository.save(emailVerification);
        }
    }
}
