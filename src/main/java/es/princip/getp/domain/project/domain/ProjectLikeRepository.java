package es.princip.getp.domain.project.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectLikeRepository extends JpaRepository<ProjectLike, Long> {

    boolean existsByPeople_PeopleIdAndProject_ProjectId(Long peopleId, Long projectId);
}
