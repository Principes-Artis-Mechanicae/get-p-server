package es.princip.getp.domain.people.service;

import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.domain.people.exception.PeopleErrorCode;
import es.princip.getp.domain.people.repository.PeopleRepository;
import es.princip.getp.global.exception.BusinessLogicException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeopleService {

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

    public Page<People> getPeoplePage(Pageable pageable) {
        Page<People> peoplPage = peopleRepository.findPeoplePage(pageable);
        return peoplPage;
    }

    @Transactional
    public People create(Member member, CreatePeopleRequest request) {
        People people = request.toEntity(member);
        return peopleRepository.save(people);
    }

    @Transactional
    public People update(Long memberId, UpdatePeopleRequest request) {
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