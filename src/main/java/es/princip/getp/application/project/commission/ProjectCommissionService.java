package es.princip.getp.application.project.commission;

import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.application.project.commission.command.CommissionProjectCommand;
import es.princip.getp.application.project.commission.port.in.CommissionProjectUseCase;
import es.princip.getp.application.project.commission.port.out.SaveProjectPort;
import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectData;
import es.princip.getp.domain.project.commission.service.ProjectCommissioner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectCommissionService implements CommissionProjectUseCase {

    private final ProjectDataMapper projectDataMapper;
    private final LoadClientPort loadClientPort;
    private final SaveProjectPort saveProjectPort;
    private final ProjectCommissioner projectCommissioner;

    @Transactional
    public Long commission(final CommissionProjectCommand command) {
        final ClientId clientId = loadClientPort.loadBy(command.memberId()).getId();
        final ProjectData data = projectDataMapper.mapToData(clientId, command);
        final Project project = projectCommissioner.commissionProject(data);
        return saveProjectPort.save(project);
    }
}
