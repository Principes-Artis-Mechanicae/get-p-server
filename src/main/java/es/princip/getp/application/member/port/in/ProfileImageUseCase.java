package es.princip.getp.application.member.port.in;

import es.princip.getp.application.member.command.RegisterProfileImageCommand;

public interface ProfileImageUseCase {

    String registerProfileImage(RegisterProfileImageCommand command);
}
