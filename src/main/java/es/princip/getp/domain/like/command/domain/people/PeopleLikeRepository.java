package es.princip.getp.domain.like.command.domain.people;

import es.princip.getp.domain.like.command.domain.LikeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleLikeRepository extends JpaRepository<PeopleLike, Long>, LikeRepository {
}