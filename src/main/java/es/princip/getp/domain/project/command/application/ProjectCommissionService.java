package es.princip.getp.domain.project.command.application;

import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.domain.project.command.application.command.RegisterProjectCommand;
import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.domain.project.command.domain.ProjectCommissioner;
import es.princip.getp.domain.project.command.domain.ProjectData;
import es.princip.getp.domain.project.command.domain.ProjectRepository;
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
    public Long commissionProject(final RegisterProjectCommand command) {
        final Long clientId = loadClientPort.loadBy(command.memberId()).getClientId();
        final ProjectData data = projectDataMapper.mapToData(clientId, command);
        final Project project = projectCommissioner.commissionProject(data);
        projectRepository.save(project);
        return project.getProjectId();
    }
}
