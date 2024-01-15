package es.princip.getp.domain.people.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequestDTO;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequestDTO;
import es.princip.getp.domain.people.dto.response.PeopleResponseDTO;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.domain.people.exception.NotFoundException;
import es.princip.getp.domain.people.repository.PeopleRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeopleService {
    private final PeopleRepository peopleRepository;

    private People getPeople(Optional<People> people) {
        return people.orElseThrow(() -> new NotFoundException());
    }

    public People getByMemberId(Long memberId) {
        return getPeople(peopleRepository.findByMember_MemberId(memberId));
    }

    public PeopleResponseDTO getResponseByPeopleId(Long peopleId) {
        return PeopleResponseDTO.from(getPeople(peopleRepository.findById(peopleId)));
    }

    public PeopleResponseDTO getResponseByMemberId(Long memberId) {
        return PeopleResponseDTO.from(getByMemberId(memberId));
    }

    @Transactional
    public PeopleResponseDTO create(Member member, CreatePeopleRequestDTO request) {
        People people = request.toEntity(member);
        peopleRepository.save(people);
        return PeopleResponseDTO.from(people);
    }

    @Transactional
    public PeopleResponseDTO update(Long memberId, UpdatePeopleRequestDTO request) {
        People people = getByMemberId(memberId);
        people.update(request);
        return PeopleResponseDTO.from(people);
    }

    @Transactional
    public void delete(Long memberId){
        People people = getByMemberId(memberId);
        peopleRepository.delete(people);
    }
}