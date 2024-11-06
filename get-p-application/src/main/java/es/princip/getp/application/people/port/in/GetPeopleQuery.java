package es.princip.getp.application.people.port.in;

import es.princip.getp.application.people.dto.response.people.CardPeopleResponse;
import es.princip.getp.application.people.dto.response.people.PeopleDetailResponse;
import es.princip.getp.application.people.dto.command.GetPeopleCommand;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.people.model.PeopleId;
import org.springframework.data.domain.Page;

public interface GetPeopleQuery {

    Page<CardPeopleResponse> getPagedCards(GetPeopleCommand command);

    PeopleDetailResponse getDetailBy(Member member, PeopleId peopleId);
}
