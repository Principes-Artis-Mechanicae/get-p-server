package es.princip.getp.domain.auth.infra;

import es.princip.getp.domain.auth.application.VerificationCodeSender;
import es.princip.getp.domain.auth.exception.FailedVerificationCodeSendingException;
import es.princip.getp.domain.member.model.Email;
import es.princip.getp.mail.EmailSender;
import es.princip.getp.mail.command.SendMailCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailVerificationCodeSender implements VerificationCodeSender {

    private final EmailSender emailSender;

    private static String title() {
        return "[GET-P] 회원가입 인증 코드";
    }

    private static String text(final String verificationCode) {
        return String.format(
            """
            안녕하십니까 GET-P입니다.
            인증 코드 번호는 %s 입니다.
            감사합니다.
            """,
            verificationCode
        );
    }

    @Override
    public void send(final Email email, final String verificationCode) {
        final SendMailCommand command = SendMailCommand.of(
            email,
            title(),
            text(verificationCode)
        );
        try {
            emailSender.send(command);
        } catch (MailException exception) {
            throw new FailedVerificationCodeSendingException();
        }
    }    
}
