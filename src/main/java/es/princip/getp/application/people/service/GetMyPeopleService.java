package es.princip.getp.application.people.service;

import es.princip.getp.api.controller.people.query.dto.people.MyPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.PeopleProfileDetailResponse;
import es.princip.getp.application.people.port.in.GetMyPeopleQuery;
import es.princip.getp.application.people.port.out.FindMyPeoplePort;
import es.princip.getp.domain.member.model.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetMyPeopleService implements GetMyPeopleQuery {

    private final FindMyPeoplePort findMyPeoplePort;

    @Override
    public MyPeopleResponse getBy(final MemberId memberId) {
        return findMyPeoplePort.findBy(memberId);
    }

    @Override
    public PeopleProfileDetailResponse getDetailProfileBy(final MemberId memberId) {
        return findMyPeoplePort.findDetailProfileBy(memberId);
    }
}