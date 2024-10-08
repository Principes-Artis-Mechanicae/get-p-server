package es.princip.getp.application.people.service;

import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.application.people.port.in.GetPeopleQuery;
import es.princip.getp.application.people.port.out.FindPeoplePort;
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
    public DetailPeopleResponse getDetailById(final Long peopleId) {
        return findPeoplePort.findDetailBy(peopleId);
    }

    @Override
    public PublicDetailPeopleResponse getPublicDetailById(final Long peopleId) {
        return findPeoplePort.findPublicDetailBy(peopleId);
    }
}