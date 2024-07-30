package es.princip.getp.domain.project.command.infra;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import es.princip.getp.domain.auth.exception.EmailErrorCode;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.project.command.application.MeetingSender;
import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectMeetingRequest;
import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.util.MailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingSenderImpl implements MeetingSender{

    private final JavaMailSender emailSender;

    @Async
    @Override
    public void send(People people, ApplyProjectMeetingRequest request) {
        SimpleMailMessage message = MailUtil.createEmailForm(people, request);
        try {
            emailSender.send(message);
        } catch (MailException mailException) {
            log.error(mailException.getMessage());
            throw new BusinessLogicException(EmailErrorCode.EMAIL_SERVER_UNAVAILABLE);
        }
    }
}
