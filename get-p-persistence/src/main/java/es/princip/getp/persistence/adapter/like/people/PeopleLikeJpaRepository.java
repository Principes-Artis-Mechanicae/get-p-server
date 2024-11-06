package es.princip.getp.persistence.adapter.like.people;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeopleLikeJpaRepository extends JpaRepository<PeopleLikeJpaEntity, Long> {

    boolean existsByMemberIdAndPeopleId(Long memberId, Long peopleId);

    Optional<PeopleLikeJpaEntity> findByMemberIdAndPeopleId(Long memberId, Long peopleId);
}