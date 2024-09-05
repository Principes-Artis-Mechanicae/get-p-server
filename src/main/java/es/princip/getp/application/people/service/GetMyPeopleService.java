package es.princip.getp.application.people.service;

import es.princip.getp.api.controller.people.query.dto.people.MyPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.application.people.port.in.GetMyPeopleQuery;
import es.princip.getp.application.people.port.out.FindMyPeoplePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetMyPeopleService implements GetMyPeopleQuery {

    private final FindMyPeoplePort findMyPeoplePort;

    @Override
    public MyPeopleResponse getByMemberId(final Long memberId) {
        return findMyPeoplePort.findBy(memberId);
    }

    @Override
    public DetailPeopleProfileResponse getDetailProfileByMemberId(final Long memberId) {
        return findMyPeoplePort.findDetailProfileBy(memberId);
    }
}