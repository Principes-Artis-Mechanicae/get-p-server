package es.princip.getp.domain.project.service;

import es.princip.getp.domain.client.entity.Client;
import es.princip.getp.domain.client.service.ClientService;
import es.princip.getp.domain.project.dto.request.CreateProjectRequest;
import es.princip.getp.domain.project.entity.Project;
import es.princip.getp.domain.project.exception.ProjectErrorCode;
import es.princip.getp.domain.project.repository.ProjectRepository;
import es.princip.getp.global.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectService {

    private final ClientService clientService;
    private final ProjectRepository projectRepository;

    @Transactional
    public Project create(Long memberId, CreateProjectRequest request) {
        Client client = clientService.getByMemberId(memberId, () -> new BusinessLogicException(
            ProjectErrorCode.NOT_REGISTERED_CLIENT));
        return projectRepository.save(request.toEntity(client));
    }
}
