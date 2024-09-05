package es.princip.getp.persistence.adapter.like.project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectLikeJpaRepository extends JpaRepository<ProjectLikeJpaEntity, Long> {
    boolean existsByPeopleIdAndProjectId(Long peopleId, Long projectId);
    
    ProjectLikeJpaEntity findByPeopleIdAndProjectId(Long peopleId, Long projectId);
}
