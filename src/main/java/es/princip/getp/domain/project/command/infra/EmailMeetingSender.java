package es.princip.getp.domain.project.command.infra;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.project.command.application.MeetingSender;
import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.domain.project.command.domain.ProjectMeeting;
import es.princip.getp.domain.project.exception.FailedMeetingSendingException;
import es.princip.getp.mail.EmailSender;
import es.princip.getp.mail.command.SendMailCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailMeetingSender implements MeetingSender {

    private final EmailSender emailSender;

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
            emailSender.send(message);
        } catch (MailException exception) {
            throw new FailedMeetingSendingException();
        }
    }
}
