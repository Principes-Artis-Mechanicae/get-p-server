package es.princip.getp.application.people.port.in;

import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PublicDetailPeopleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetPeopleQuery {

    Page<CardPeopleResponse> getPagedCards(Pageable pageable);

    DetailPeopleResponse getDetailById(Long peopleId);

    PublicDetailPeopleResponse getPublicDetailById(Long peopleId);
}
