package es.princip.getp.domain.people.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.princip.getp.domain.people.dto.request.CreatePeopleProfileRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleProfileRequest;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleProfile;
import es.princip.getp.domain.people.exception.PeopleProfileErrorCode;
import es.princip.getp.domain.people.repository.PeopleProfileRepository;
import es.princip.getp.infra.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeopleProfileService {
    private final PeopleService peopleService;

    private final PeopleProfileRepository peopleProfileRepository;

    private PeopleProfile get(Optional<PeopleProfile> peopleProfile) {
        return peopleProfile.orElseThrow(() -> new BusinessLogicException(PeopleProfileErrorCode.PEOPLE_PROFILE_NOT_FOUND));
    }

    public PeopleProfile getByMemberId(Long memberId) {
        return get(peopleProfileRepository.findByMemberId(memberId));
    }

    public PeopleProfile getByPeopleId(Long peopleId) {
        return get(peopleProfileRepository.findByPeople_PeopleId(peopleId));
    }

    @Transactional
    public PeopleProfile create(Long memberId, CreatePeopleProfileRequest request) {
        People people = peopleService.getByMemberId(memberId);
        PeopleProfile peopleProfile = request.toEntity(people);
        return peopleProfileRepository.save(peopleProfile);
    }

    @Transactional
    public PeopleProfile update(Long memberId, UpdatePeopleProfileRequest request) {
        PeopleProfile peopleProfile = getByMemberId(memberId);
        peopleProfile.update(request);
        return peopleProfile;
    }
}
