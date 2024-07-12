package es.princip.getp.domain.people.infra;

import es.princip.getp.domain.people.presentation.dto.response.people.CardPeopleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PeopleQueryDslRepository {

    Page<CardPeopleResponse> findCardPeoplePage(Pageable pageable);
}
