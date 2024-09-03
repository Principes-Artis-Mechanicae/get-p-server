package es.princip.getp.persistence.adapter.people;

import es.princip.getp.persistence.adapter.people.model.PeopleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface PeopleJpaRepository extends JpaRepository<PeopleJpaEntity, Long> {

    Optional<PeopleJpaEntity> findByMemberId(Long memberId);

    boolean existsByMemberId(Long memberId);
}
