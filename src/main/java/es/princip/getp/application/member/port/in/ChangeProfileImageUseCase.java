package es.princip.getp.application.member.port.in;

import es.princip.getp.application.member.command.ChangeProfileImageCommand;

public interface ChangeProfileImageUseCase {

    String changeProfileImage(ChangeProfileImageCommand command);
}
