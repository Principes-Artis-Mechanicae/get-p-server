package es.princip.getp.domain.people.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.princip.getp.domain.people.entity.People;

public interface PeopleRepository extends JpaRepository<People, Long>{

}
