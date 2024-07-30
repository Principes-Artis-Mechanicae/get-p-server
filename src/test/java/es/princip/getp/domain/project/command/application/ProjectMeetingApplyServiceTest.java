package es.princip.getp.domain.project.command.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.domain.people.fixture.PeopleFixture;
import es.princip.getp.domain.project.command.domain.ProjectApplicationRepository;
import es.princip.getp.domain.project.command.infra.MeetingSenderImpl;
import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectMeetingRequest;
import es.princip.getp.domain.project.fixture.ProjectMeetingApplyFixture;

@ExtendWith(MockitoExtension.class)
public class ProjectMeetingApplyServiceTest {

        @Mock
        private JavaMailSender emailSender;

        @Mock
        private PeopleRepository peopleRepository;

        @Mock
        private ProjectApplicationRepository projectApplicationRepository;

        @InjectMocks
        private MeetingSenderImpl meetingSender;

    @Nested
    @DisplayName("프로젝트 미팅 신청은")
    class ApplyProjectMeeting{
        private final People command = PeopleFixture.people(
            1L, PeopleType.INDIVIDUAL
        );

        private final ApplyProjectMeetingRequest request = ProjectMeetingApplyFixture.applyProjectMeetingRequest();

        @Test
        @DisplayName("이메일을 전송한다.")
        void sendEmail() {
            //TODO: ProjectApplicationFixture 생성 후 작성
            
            meetingSender.send(command, request);

            verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
        }
    }
}
