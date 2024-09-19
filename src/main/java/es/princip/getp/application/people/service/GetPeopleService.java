package es.princip.getp.application.people.service;

import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.application.people.port.in.GetPeopleQuery;
import es.princip.getp.application.people.port.out.FindPeoplePort;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetPeopleService implements GetPeopleQuery {

    private final FindPeoplePort findPeoplePort;

    @Override
    public Page<CardPeopleResponse> getPagedCards(final Pageable pageable) {
        return findPeoplePort.findCardBy(pageable);
    }

    @Override
    public DetailPeopleResponse getDetailBy(final MemberId memberId, final PeopleId peopleId) {
        return findPeoplePort.findDetailBy(memberId, peopleId);
    }

    @Override
    public PublicDetailPeopleResponse getPublicDetailBy(final PeopleId peopleId) {
        return findPeoplePort.findPublicDetailBy(peopleId);
    }
}