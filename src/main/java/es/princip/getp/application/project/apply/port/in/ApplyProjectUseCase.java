package es.princip.getp.application.project.apply.port.in;

import es.princip.getp.application.project.apply.command.ApplyProjectCommand;

public interface ApplyProjectUseCase {

    Long apply(final ApplyProjectCommand command);
}
