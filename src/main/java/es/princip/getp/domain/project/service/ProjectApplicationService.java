package es.princip.getp.domain.project.service;

import es.princip.getp.domain.people.entity.People;
import es.princip.getp.domain.people.service.PeopleService;
import es.princip.getp.domain.project.dto.request.ApplicateProjectRequest;
import es.princip.getp.domain.project.entity.Project;
import es.princip.getp.domain.project.entity.ProjectApplication;
import es.princip.getp.domain.project.repository.ProjectApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectApplicationService {

    private final ProjectApplicationRepository projectApplicationRepository;
    private final PeopleService peopleService;
    private final ProjectService projectService;

    @Transactional
    public ProjectApplication create(Long memberId, Long projectId, ApplicateProjectRequest request) {
        People people = peopleService.getByMemberId(memberId);
        Project project = projectService.getByProjectId(projectId);
        return projectApplicationRepository.save(request.toEntity(people, project));
    }
}
