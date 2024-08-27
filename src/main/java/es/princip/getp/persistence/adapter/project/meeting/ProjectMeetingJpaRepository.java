package es.princip.getp.persistence.adapter.project.meeting;

import es.princip.getp.persistence.adapter.project.meeting.entity.ProjectMeetingJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface ProjectMeetingJpaRepository extends JpaRepository<ProjectMeetingJpaEntity, Long> {
}
