package es.princip.getp.domain.people.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleProfileRepository extends JpaRepository<PeopleProfile, Long> {

    Optional<PeopleProfile> findByPeopleId(Long peopleId);

    boolean existsByPeopleId(Long peopleId);
}
