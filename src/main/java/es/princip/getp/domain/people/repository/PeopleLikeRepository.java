package es.princip.getp.domain.people.repository;

import es.princip.getp.domain.people.domain.PeopleLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleLikeRepository extends JpaRepository<PeopleLike, Long> {

    boolean existsByClient_ClientIdAndPeople_PeopleId(Long clientId, Long peopleId);
}