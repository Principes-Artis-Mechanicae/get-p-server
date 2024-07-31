package es.princip.getp.domain.auth.infra;

import es.princip.getp.domain.auth.application.VerificationCodeSender;
import es.princip.getp.domain.auth.exception.EmailErrorCode;
import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.util.MailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationSenderImpl implements VerificationCodeSender {

    private final JavaMailSender emailSender;
    
    @Async
    @Override
    public void send(Email email, String text) {
        SimpleMailMessage message = MailUtil.createEmailForm(email, text);
        try {
            emailSender.send(message);
        } catch (MailException mailException) {
            log.error(mailException.getMessage());
            throw new BusinessLogicException(EmailErrorCode.EMAIL_SERVER_UNAVAILABLE);
        }
    }
}
