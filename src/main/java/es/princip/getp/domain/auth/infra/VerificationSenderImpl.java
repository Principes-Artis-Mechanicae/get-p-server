package es.princip.getp.domain.auth.infra;

import es.princip.getp.domain.auth.application.VerificationCodeSender;
import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.infra.MailSender;

import lombok.RequiredArgsConstructor;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationSenderImpl implements VerificationCodeSender {

    private final MailSender mailSender;

    private static String title() {
        String title = "[GET-P] 회원가입 인증 코드";
        return title;
    }

    private static String text(String codeNumber) {
        return String.format("안녕하십니까 GET-P입니다. \n\n 인증 코드 번호는 %s 입니다. \n\n 감사합니다.", codeNumber);
    }

    @Override
    public void send(Email email, String verificationCode) {
        SimpleMailMessage message = MailSender.createSimpleMailMessage(email.getValue(), title(), text(verificationCode));
        mailSender.send(message);
    }    
}
