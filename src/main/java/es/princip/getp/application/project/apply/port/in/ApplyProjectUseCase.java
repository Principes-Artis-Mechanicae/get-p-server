package es.princip.getp.application.project.apply.port.in;

import es.princip.getp.application.project.apply.command.ApplyProjectCommand;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;

public interface ApplyProjectUseCase {

    ProjectApplicationId apply(final ApplyProjectCommand command);
}
