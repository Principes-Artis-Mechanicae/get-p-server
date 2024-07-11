package es.princip.getp.domain.people.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeopleLikeRepository extends JpaRepository<PeopleLike, Long> {

    boolean existsByClient_ClientIdAndPeople_PeopleId(Long clientId, Long peopleId);

    Optional<PeopleLike> findByPeople_PeopleIdAndClient_ClientId(Long peopleId, Long clientId);
}