package es.princip.getp.domain.people.repository;

import es.princip.getp.domain.people.entity.People;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PeopleQueryDslRepository {

    Page<People> findPeoplePage(Pageable pageable);
}
