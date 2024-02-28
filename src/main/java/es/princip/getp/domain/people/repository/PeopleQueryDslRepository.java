package es.princip.getp.domain.people.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import es.princip.getp.domain.people.dto.response.people.CardPeopleResponse;

public interface PeopleQueryDslRepository {

    Page<CardPeopleResponse> findCardPeoplePage(Pageable pageable);
}
