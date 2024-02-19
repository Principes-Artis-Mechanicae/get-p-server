package es.princip.getp.domain.people.repository;

import es.princip.getp.domain.people.entity.People;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long>, PeopleQueryDslRepository {
    Optional<People> findByMember_MemberId(Long memberId);
}
