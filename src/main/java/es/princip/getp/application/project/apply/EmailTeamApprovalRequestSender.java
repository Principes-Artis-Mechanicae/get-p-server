package es.princip.getp.application.project.apply;

import es.princip.getp.application.mail.command.SendMailCommand;
import es.princip.getp.application.mail.port.in.SendMailUseCase;
import es.princip.getp.application.project.apply.exception.FailedTeamApprovalRequestSendingException;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.project.commission.model.Project;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
class EmailTeamApprovalRequestSender implements TeamApprovalRequestSender {

    private final TemplateEngine templateEngine;
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
        final Context context = new Context();
        context.setVariable("requester", requester);
        context.setVariable("project", project);
        context.setVariable("approvalLink", approvalLink);
        return templateEngine.process("teammate-approval", context);
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
        } catch (MailException | MessagingException exception) {
            throw new FailedTeamApprovalRequestSendingException();
        }
    }
}
