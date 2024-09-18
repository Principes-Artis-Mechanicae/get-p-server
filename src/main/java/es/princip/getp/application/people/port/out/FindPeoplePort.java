package es.princip.getp.application.people.port.out;

import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.domain.people.model.PeopleId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindPeoplePort {

    Page<CardPeopleResponse> findCardBy(Pageable pageable);

    DetailPeopleResponse findDetailBy(PeopleId peopleId);

    PublicDetailPeopleResponse findPublicDetailBy(PeopleId peopleId);
}
