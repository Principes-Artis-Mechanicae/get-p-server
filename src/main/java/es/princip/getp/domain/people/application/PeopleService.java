package es.princip.getp.domain.people.application;

import es.princip.getp.domain.member.application.MemberService;
import es.princip.getp.domain.member.dto.request.UpdateMemberRequest;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleRepository;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;
import es.princip.getp.domain.people.dto.response.people.CardPeopleResponse;
import es.princip.getp.domain.people.exception.PeopleErrorCode;
import es.princip.getp.infra.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeopleService {

    private final MemberService memberService;
    private final PeopleRepository peopleRepository;

    private People get(Optional<People> people) {
        return people.orElseThrow(
            () -> new BusinessLogicException(PeopleErrorCode.PEOPLE_NOT_FOUND));
    }

    public People getByMemberId(Long memberId) {
        return get(peopleRepository.findByMember_MemberId(memberId));
    }

    public People getByPeopleId(Long memberId) {
        return get(peopleRepository.findById(memberId));
    }

    public Page<CardPeopleResponse> getCardPeoplePage(Pageable pageable) {
        return peopleRepository.findCardPeoplePage(pageable);
    }

    @Transactional
    public People create(Long memberId, CreatePeopleRequest request) {
        memberService.update(memberId, UpdateMemberRequest.from(request));
        People people = People.from(memberService.getByMemberId(memberId), request);
        return peopleRepository.save(people);
    }

    @Transactional
    public People update(Long memberId, UpdatePeopleRequest request) {
        memberService.update(memberId, UpdateMemberRequest.from(request));
        People people = getByMemberId(memberId);
        people.update(request);
        return people;
    }

    @Transactional
    public void delete(Long memberId) {
        People people = getByMemberId(memberId);
        peopleRepository.delete(people);
    }
}