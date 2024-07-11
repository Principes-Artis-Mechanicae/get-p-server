package es.princip.getp.domain.people.domain;

import es.princip.getp.domain.people.infra.PeopleQueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long>, PeopleQueryDslRepository {
    Optional<People> findByMember_MemberId(Long memberId);
}
