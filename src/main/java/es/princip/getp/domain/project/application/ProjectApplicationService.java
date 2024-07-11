package es.princip.getp.domain.project.application;

import es.princip.getp.domain.people.application.PeopleService;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.project.domain.Project;
import es.princip.getp.domain.project.domain.ProjectApplication;
import es.princip.getp.domain.project.domain.ProjectApplicationRepository;
import es.princip.getp.domain.project.dto.request.ApplicateProjectRequest;
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