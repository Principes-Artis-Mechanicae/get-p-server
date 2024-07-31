package es.princip.getp.infra.util;

import java.util.stream.Collectors;

import org.springframework.mail.SimpleMailMessage;

import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.project.command.domain.MeetingSchedule;
import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectMeetingRequest;

public class MailUtil {

    private static SimpleMailMessage createSimpleMailMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }

    public static SimpleMailMessage createEmailForm(Email email, String codeNumber) {
        String title = "[GET-P] 회원가입 인증 코드";
        String text = createSignupText(codeNumber);
        
        return createSimpleMailMessage(email.getValue(), title, text);
    }

    private static String createSignupText(String codeNumber) {
        return String.format("안녕하십니까 GET-P입니다. \n\n 인증 코드 번호는 %s 입니다. \n\n 감사합니다.", codeNumber);
    }

    public static SimpleMailMessage createEmailForm(People people, ApplyProjectMeetingRequest request) {
        String title = String.format("[GET-P] %s 미팅 신청", request.projectName());
        String text = createMeetingRequestText(request);
        
        return createSimpleMailMessage(people.getEmail().getValue(), title, text);
    }

    private static String createMeetingRequestText(ApplyProjectMeetingRequest request) {
        return String.format(
            "안녕하십니까 GET-P입니다. \n\n" + "고객님이 지원해 주신 %s 프로젝트 의뢰자님이 미팅 신청을 하셨습니다. "
            + "\n\n \t원하시는 미팅 장소 : %s \n\n \t원하시는 미팅 시간 : %s \t내용 : %s"
            + "\n\n 미팅을 수락하신다면 해당 메일로 회신 바랍니다.",
            request.projectName(), 
            request.meetingLocation(), 
            request.meetingSchedule().stream()
                .map(MeetingSchedule::formatDateTime)
                .collect(Collectors.joining("")), 
            request.description());
        }
}