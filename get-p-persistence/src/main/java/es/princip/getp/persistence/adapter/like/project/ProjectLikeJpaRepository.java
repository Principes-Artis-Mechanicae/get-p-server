package es.princip.getp.persistence.adapter.like.project;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectLikeJpaRepository extends JpaRepository<ProjectLikeJpaEntity, Long> {

    boolean existsByMemberIdAndProjectId(Long memberId, Long projectId);

    Optional<ProjectLikeJpaEntity> findByMemberIdAndProjectId(Long memberId, Long projectId);
}
