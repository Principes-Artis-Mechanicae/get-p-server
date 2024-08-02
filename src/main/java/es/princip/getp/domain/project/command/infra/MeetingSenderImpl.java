package es.princip.getp.domain.project.command.infra;

import java.util.stream.Collectors;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import es.princip.getp.domain.common.domain.MeetingSchedule;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.project.command.application.MeetingSender;
import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.domain.project.command.domain.ProjectMeeting;
import es.princip.getp.infra.MailSender;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MeetingSenderImpl implements MeetingSender{

    private final MailSender mailSender;

    private String text(Project project, ProjectMeeting projectMeeting) {
        return String.format(
            "안녕하십니까 GET-P입니다. \n\n" + "고객님이 지원해 주신 %s 프로젝트 의뢰자님이 미팅 신청을 하셨습니다. "
            + "\n\n \t원하시는 미팅 장소 : %s \n\n \t원하시는 미팅 시간 : %s \t내용 : %s"
            + "\n\n 미팅을 수락하신다면 해당 메일로 회신 바랍니다.",
            project.getTitle(), 
            projectMeeting.getMeetingLocation(), 
            projectMeeting.getMeetingSchedules().stream()
                .map(MeetingSchedule::toString)
                .collect(Collectors.joining("")), 
            projectMeeting.getDescription()
        );
    }

    @Override
    public void send(People people, Project project, ProjectMeeting projectMeeting) {
        SimpleMailMessage message = MailSender.createSimpleMailMessage(
            people.getEmail().getValue(),
            String.format("[GET-P] %s 미팅 신청",(project.getTitle())),
            text(project, projectMeeting)
        );
        mailSender.send(message);
    }
}
