package es.princip.getp.persistence.adapter.like.project;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectLikeJpaRepository extends JpaRepository<ProjectLikeJpaEntity, Long> {

    boolean existsByPeopleIdAndProjectId(Long peopleId, Long projectId);

    Optional<ProjectLikeJpaEntity> findByPeopleIdAndProjectId(Long peopleId, Long projectId);
}
