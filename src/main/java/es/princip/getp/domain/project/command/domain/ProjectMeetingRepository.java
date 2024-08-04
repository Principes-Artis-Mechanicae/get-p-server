package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.project.command.infra.ProjectMeetingQueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMeetingRepository extends JpaRepository<ProjectMeeting, Long>, ProjectMeetingQueryDslRepository {
    
}
