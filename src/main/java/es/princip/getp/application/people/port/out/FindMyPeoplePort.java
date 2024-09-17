package es.princip.getp.application.people.port.out;

import es.princip.getp.api.controller.people.query.dto.people.MyPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.domain.member.model.MemberId;

public interface FindMyPeoplePort {

    MyPeopleResponse findBy(MemberId memberId);

    DetailPeopleProfileResponse findDetailProfileBy(MemberId memberId);
}
