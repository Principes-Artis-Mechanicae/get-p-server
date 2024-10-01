package es.princip.getp.application.project.apply;

import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.project.commission.model.Project;

public interface TeamApprovalRequestSender {

    void send(Member requester, People receiver, Project project, String approvalLink);
}
