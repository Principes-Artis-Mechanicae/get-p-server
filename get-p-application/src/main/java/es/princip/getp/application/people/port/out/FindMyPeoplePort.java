package es.princip.getp.application.people.port.out;

import es.princip.getp.application.people.dto.response.people.MyPeopleResponse;
import es.princip.getp.application.people.dto.response.peopleProfile.PeopleProfileDetailResponse;
import es.princip.getp.domain.member.model.MemberId;

public interface FindMyPeoplePort {

    MyPeopleResponse findBy(MemberId memberId);

    PeopleProfileDetailResponse findDetailProfileBy(MemberId memberId);
}
