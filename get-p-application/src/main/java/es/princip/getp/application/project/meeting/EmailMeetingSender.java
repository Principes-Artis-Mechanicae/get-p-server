package es.princip.getp.application.project.meeting;

import es.princip.getp.application.mail.command.SendMailCommand;
import es.princip.getp.application.mail.port.in.SendMailUseCase;
import es.princip.getp.application.project.meeting.exception.FailedMeetingSendingException;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.meeting.model.ProjectMeeting;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailMeetingSender implements MeetingSender {

    private final SendMailUseCase sendMailUseCase;

    private String title(final Project project) {
        return String.format("[GET-P] %s 미팅 신청", project.getTitle());
    }

    private String text(final Project project, final ProjectMeeting meeting) {
        return String.format(
            """
            안녕하십니까 GET-P입니다.
            고객님이 지원해 주신 프로젝트 %s의 의뢰자님이 미팅을 신청 하셨습니다.
            
            원하시는 미팅 장소 : %s
            원하시는 미팅 시간 : %s
            내용 : %s
            
            미팅을 수락하신다면 본 메일로 회신 부탁드립니다.",
            """,
            project.getTitle(), 
            meeting.getLocation(),
            meeting.getSchedule(),
            meeting.getDescription()
        );
    }

    @Override
    public void send(
        final People people,
        final Project project,
        final ProjectMeeting meeting
    ) {
        final SendMailCommand message = SendMailCommand.of(
            people.getInfo().getEmail(),
            title(project),
            text(project, meeting)
        );
        try {
            sendMailUseCase.send(message);
        } catch (MailException | MessagingException exception) {
            throw new FailedMeetingSendingException();
        }
    }
}
