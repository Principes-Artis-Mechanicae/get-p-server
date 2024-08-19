package es.princip.getp.application.member.port.in;

import es.princip.getp.application.member.command.EditMemberCommand;

public interface EditMemberUseCase {

    void editMember(EditMemberCommand command);
}
