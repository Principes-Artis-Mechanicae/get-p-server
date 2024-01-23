package es.princip.getp.domain.people.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import es.princip.getp.domain.people.entity.People;

public interface PeopleRepository extends JpaRepository<People, Long>{
    Optional<People> findByMember_MemberId(Long memberId);
}
