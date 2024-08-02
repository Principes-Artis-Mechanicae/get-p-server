package es.princip.getp.domain.project.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectLikeRepository extends JpaRepository<ProjectLike, Long> {

    boolean existsByPeopleIdAndProjectId(Long peopleId, Long projectId);

    void deleteByPeopleIdAndProjectId(Long peopleId, Long projectId);
}