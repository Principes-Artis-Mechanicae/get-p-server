package es.princip.getp.domain.project.service;

import es.princip.getp.domain.client.entity.Client;
import es.princip.getp.domain.client.service.ClientService;
import es.princip.getp.domain.project.dto.request.CreateProjectRequest;
import es.princip.getp.domain.project.entity.Project;
import es.princip.getp.domain.project.exception.ProjectErrorCode;
import es.princip.getp.domain.project.repository.ProjectRepository;
import es.princip.getp.global.exception.BusinessLogicException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectService {

    private final ClientService clientService;
    private final ProjectRepository projectRepository;

    private Project get(Optional<Project> project) {
        return project.orElseThrow(() -> new BusinessLogicException(ProjectErrorCode.PROJECT_NOT_FOUND));
    }

    @Transactional
    public Project create(Long memberId, CreateProjectRequest request) {
        Client client = clientService.getByMemberId(memberId, () -> new BusinessLogicException(
            ProjectErrorCode.NOT_REGISTERED_CLIENT));
        return projectRepository.save(request.toEntity(client));
    }

    public Page<Project> getProjectPage(Pageable pageable) {
        return projectRepository.findProjectPage(pageable);
    }

    public Project getByProjectId(Long projectId) {
        return get(projectRepository.findById(projectId));
    }
}
