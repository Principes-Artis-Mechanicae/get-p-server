package es.princip.getp.application.people.port.in;

import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.application.people.command.GetPeopleCommand;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;
import org.springframework.data.domain.Page;

public interface GetPeopleQuery {

    Page<CardPeopleResponse> getPagedCards(GetPeopleCommand command);

    DetailPeopleResponse getDetailBy(MemberId memberId, PeopleId peopleId);

    PublicDetailPeopleResponse getPublicDetailBy(PeopleId peopleId);
}
