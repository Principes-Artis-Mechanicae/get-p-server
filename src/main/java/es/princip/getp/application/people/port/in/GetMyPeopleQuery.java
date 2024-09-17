package es.princip.getp.application.people.port.in;

import es.princip.getp.api.controller.people.query.dto.people.MyPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.domain.member.model.MemberId;

public interface GetMyPeopleQuery {

    MyPeopleResponse getBy(MemberId memberId);

    DetailPeopleProfileResponse getDetailProfileBy(MemberId memberId);
}
