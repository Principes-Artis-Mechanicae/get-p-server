package es.princip.getp.application.auth.infra;

import es.princip.getp.application.auth.exception.FailedVerificationCodeSendingException;
import es.princip.getp.application.auth.service.VerificationCodeSender;
import es.princip.getp.application.mail.command.SendMailCommand;
import es.princip.getp.application.mail.port.in.SendMailUseCase;
import es.princip.getp.domain.member.model.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailVerificationCodeSender implements VerificationCodeSender {

    private final SendMailUseCase sendMailUseCase;

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
            sendMailUseCase.send(command);
        } catch (MailException exception) {
            throw new FailedVerificationCodeSendingException();
        }
    }    
}
