package es.princip.getp.application.people.port.in;

import es.princip.getp.api.controller.people.query.dto.people.MyPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;

public interface GetMyPeopleQuery {

    MyPeopleResponse getByMemberId(Long memberId);

    DetailPeopleProfileResponse getDetailProfileByMemberId(Long memberId);
}
