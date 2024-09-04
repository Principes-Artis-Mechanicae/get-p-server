package es.princip.getp.persistence.adapter.like.command.people;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleLikeJpaRepository extends JpaRepository<PeopleLikeJpaEntity, Long> {
    boolean existsByClientIdAndPeopleId(Long clientId, Long peopleId);

    PeopleLikeJpaEntity findByClientIdAndPeopleId(Long clientId, Long peopleId);
}