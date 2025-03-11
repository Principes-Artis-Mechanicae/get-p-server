package es.princip.getp.application.people.port.out;

import es.princip.getp.api.controller.people.query.dto.people.MyPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;

public interface FindMyPeoplePort {

    MyPeopleResponse findBy(Long memberId);

    DetailPeopleProfileResponse findDetailProfileBy(Long memberId);
}
