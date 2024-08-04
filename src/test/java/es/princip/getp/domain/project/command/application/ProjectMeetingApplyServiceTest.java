package es.princip.getp.domain.project.command.application;

import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.project.command.domain.ProjectApplicationRepository;
import es.princip.getp.domain.project.command.infra.MailMeetingSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
public class ProjectMeetingApplyServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private PeopleRepository peopleRepository;

    @Mock
    private ProjectApplicationRepository projectApplicationRepository;

    @InjectMocks
    private MailMeetingSender meetingSender;

    @Nested
    @DisplayName("프로젝트 미팅 신청은")
    class ApplyProjectMeeting{

        @Test
        @DisplayName("이메일을 전송한다.")
        void sendEmail() {

        }
    }
}
