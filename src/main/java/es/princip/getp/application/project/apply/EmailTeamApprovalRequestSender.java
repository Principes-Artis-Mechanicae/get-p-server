package es.princip.getp.application.project.apply;

import es.princip.getp.application.mail.command.SendMailCommand;
import es.princip.getp.application.mail.port.in.SendMailUseCase;
import es.princip.getp.application.project.apply.exception.FailedTeamApprovalRequestSendingException;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.project.commission.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class EmailTeamApprovalRequestSender implements TeamApprovalRequestSender {

    private final SendMailUseCase sendMailUseCase;

    private String title(final Member requester, final Project project) {
        return String.format(
            """
            [GET-P] %s님이 %s 프로젝트를 함께 지원하고 싶어해요.
            """,
            requester.getNickname(),
            project.getTitle()
        );
    }

    private String text(
        final Member requester,
        final Project project,
        final String approvalLink
    ) {
        return String.format(
            """
            %s님이 "%s"(을)를 함께 지원하고 싶어해요.
            프로젝트 지원에 동의하시면, 아래의 팀원 승인 신청 링크를 클릭해주세요.
            
            %s
            """,
            requester.getNickname(),
            project.getTitle(),
            approvalLink
        );
    }

    @Override
    public void send(
        final Member requester,
        final People receiver,
        final Project project,
        final String approvalLink
    ) {
        final SendMailCommand message = SendMailCommand.of(
            receiver.getInfo().getEmail(),
            title(requester, project),
            text(requester, project, approvalLink)
        );
        try {
            sendMailUseCase.send(message);
        } catch (MailException exception) {
            throw new FailedTeamApprovalRequestSendingException();
        }
    }
}
