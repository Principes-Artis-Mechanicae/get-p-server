package es.princip.getp.persistence.adapter.like.people;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeopleLikeJpaRepository extends JpaRepository<PeopleLikeJpaEntity, Long> {

    boolean existsByClientIdAndPeopleId(Long clientId, Long peopleId);

    Optional<PeopleLikeJpaEntity> findByClientIdAndPeopleId(Long clientId, Long peopleId);
}