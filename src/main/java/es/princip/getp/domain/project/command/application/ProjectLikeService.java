package es.princip.getp.domain.project.command.application;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.project.command.domain.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectLikeService {

    private final ProjectRepository projectRepository;
    private final PeopleRepository peopleRepository;

    @Transactional
    public void like(Long memberId, Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project does not exist");
        }
        People people = peopleRepository.findByMemberId(memberId).orElseThrow();
        people.likeProject(projectId);
    }
}
