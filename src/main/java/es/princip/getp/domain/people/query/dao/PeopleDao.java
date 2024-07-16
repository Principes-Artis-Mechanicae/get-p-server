package es.princip.getp.domain.people.query.dao;

import es.princip.getp.domain.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.domain.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.domain.people.query.dto.people.PeopleResponse;
import es.princip.getp.domain.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.domain.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PeopleDao {

    Page<CardPeopleResponse> findCardPeoplePage(Pageable pageable);

    Optional<DetailPeopleResponse> findDetailPeopleById(Long peopleId);

    Optional<PublicDetailPeopleResponse> findPublicDetailPeopleById(Long peopleId);

    Optional<PeopleResponse> findByMemberId(Long memberId);

    Optional<DetailPeopleProfileResponse> findDetailPeopleProfileByMemberId(Long memberId);
}
