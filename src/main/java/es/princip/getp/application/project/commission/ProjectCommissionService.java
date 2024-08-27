package es.princip.getp.application.project.commission;

import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.application.project.commission.command.CommissionProjectCommand;
import es.princip.getp.domain.project.commission.ProjectRepository;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectData;
import es.princip.getp.domain.project.commission.service.ProjectCommissioner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectCommissionService {

    private final ProjectDataMapper projectDataMapper;
    private final LoadClientPort loadClientPort;
    private final ProjectRepository projectRepository;
    private final ProjectCommissioner projectCommissioner;

    @Transactional
    public Long commissionProject(final CommissionProjectCommand command) {
        final Long clientId = loadClientPort.loadBy(command.memberId()).getClientId();
        final ProjectData data = projectDataMapper.mapToData(clientId, command);
        final Project project = projectCommissioner.commissionProject(data);
        projectRepository.save(project);
        return project.getProjectId();
    }
}
