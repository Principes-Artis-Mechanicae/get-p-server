package es.princip.getp.domain.people.query.dao;

import es.princip.getp.domain.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.domain.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.domain.people.query.dto.people.MyPeopleResponse;
import es.princip.getp.domain.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.domain.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PeopleDao {

    Page<CardPeopleResponse> findCardPeoplePage(Pageable pageable);

    DetailPeopleResponse findDetailPeopleById(Long peopleId);

    PublicDetailPeopleResponse findPublicDetailPeopleById(Long peopleId);

    MyPeopleResponse findByMemberId(Long memberId);

    DetailPeopleProfileResponse findDetailPeopleProfileByMemberId(Long memberId);
}
