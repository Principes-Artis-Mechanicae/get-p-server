package es.princip.getp.domain.project.application;

import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.client.domain.ClientRepository;
import es.princip.getp.domain.project.domain.Project;
import es.princip.getp.domain.project.domain.ProjectRepository;
import es.princip.getp.domain.project.dto.request.CreateProjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectService {

    private final ClientRepository clientRepository;
    private final ProjectRepository projectRepository;


    @Transactional
    public void enroll(Long memberId, CreateProjectRequest request) {
        Client client = clientRepository.findByMemberId(memberId).orElseThrow();
        Project project = Project.builder()
            .clientId(client.getClientId())
            .title(request.title())
            .description(request.description())
            .build();
        projectRepository.save(project);
    }
}
