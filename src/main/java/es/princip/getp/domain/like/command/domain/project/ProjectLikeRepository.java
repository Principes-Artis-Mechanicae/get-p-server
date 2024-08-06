package es.princip.getp.domain.like.command.domain.project;

import es.princip.getp.domain.like.command.domain.LikeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectLikeRepository extends JpaRepository<ProjectLike, Long>, LikeRepository {
}