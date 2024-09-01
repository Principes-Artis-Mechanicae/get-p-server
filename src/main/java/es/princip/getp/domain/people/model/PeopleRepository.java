package es.princip.getp.domain.people.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {

    Optional<People> findByMemberId(Long memberId);

    boolean existsByMemberId(Long memberId);
}
