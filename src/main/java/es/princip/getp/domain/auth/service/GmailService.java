package es.princip.getp.domain.auth.service;

import org.springframework.context.annotation.Primary;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import es.princip.getp.domain.auth.exception.EmailExceptionCode;
import es.princip.getp.global.exception.BusinessLogicException;
import es.princip.getp.global.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Primary
@RequiredArgsConstructor
@Service
public class GmailService implements EmailService {
    private final JavaMailSender emailSender;
    
    private SimpleMailMessage createEmailForm(String email, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(title);
        message.setText(text);
        return message;
    }

    public void sendEmail(String email, String title, String text) {
        if (!EmailUtil.isValidEmail(email)) {
            throw new BusinessLogicException(EmailExceptionCode.WRONG_EMAIL);
        }
        SimpleMailMessage emailForm = createEmailForm(email, title, text);
        try {
            emailSender.send(emailForm);
        } catch (MailParseException mailParseException) {
            log.debug("GmailService.sendEmail() 메일 파싱 오류 email: {}, title: {}, text: {}", email, title, text);
            throw new BusinessLogicException(EmailExceptionCode.EMAIL_SERVER_UNAVAILABLE);
        } catch (MailAuthenticationException mailAuthenticationException) {
            log.debug("GmailService.sendEmail() 메일 인증 오류 email: {}, title: {}, text: {}", email, title, text);
            throw new BusinessLogicException(EmailExceptionCode.EMAIL_SERVER_UNAVAILABLE);
        } catch (MailSendException mailSendException) {
            log.debug("GmailService.sendEmail() 메일 전송 오류 email: {}, title: {}, text: {}", email, title, text);
            throw new BusinessLogicException(EmailExceptionCode.EMAIL_SERVER_UNAVAILABLE);
        }
    }
}
