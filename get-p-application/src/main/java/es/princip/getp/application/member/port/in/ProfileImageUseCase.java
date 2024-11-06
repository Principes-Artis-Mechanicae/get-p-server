package es.princip.getp.application.member.port.in;

import es.princip.getp.application.member.dto.command.RegisterProfileImageCommand;

public interface ProfileImageUseCase {

    String registerProfileImage(RegisterProfileImageCommand command);
}
