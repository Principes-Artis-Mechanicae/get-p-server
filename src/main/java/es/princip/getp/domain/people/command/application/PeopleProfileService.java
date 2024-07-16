package es.princip.getp.domain.people.command.application;

import es.princip.getp.domain.people.command.domain.PeopleProfile;
import es.princip.getp.domain.people.command.domain.PeopleProfileRepository;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.people.command.presentation.dto.request.EditPeopleProfileRequest;
import es.princip.getp.domain.people.command.presentation.dto.request.WritePeopleProfileRequest;
import es.princip.getp.domain.people.exception.PeopleErrorCode;
import es.princip.getp.domain.people.exception.PeopleProfileErrorCode;
import es.princip.getp.infra.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeopleProfileService {

    private final PeopleProfileRepository peopleProfileRepository;
    private final PeopleRepository peopleRepository;

    @Transactional
    public void writeProfile(Long memberId, WritePeopleProfileRequest request) {
        Long peopleId = peopleRepository.findByMemberId(memberId)
            .orElseThrow(
                () -> new BusinessLogicException(PeopleErrorCode.NOT_FOUND)
            )
            .getPeopleId();
        if (peopleProfileRepository.existsByPeopleId(peopleId)) {
            throw new BusinessLogicException(PeopleProfileErrorCode.ALREADY_EXISTS);
        }
        PeopleProfile profile = PeopleProfile.builder()
            .introduction(request.introduction())
            .activityArea(request.activityArea())
            .education(request.education())
            .hashtags(request.hashtags())
            .techStacks(request.techStacks())
            .portfolios(request.portfolios())
            .peopleId(peopleId)
            .build();
        peopleProfileRepository.save(profile);
    }

    @Transactional
    public void editProfile(Long memberId, EditPeopleProfileRequest request) {
        Long peopleId = peopleRepository.findByMemberId(memberId)
            .orElseThrow(
                () -> new BusinessLogicException(PeopleErrorCode.NOT_FOUND)
            )
            .getPeopleId();
        PeopleProfile profile = peopleProfileRepository.findByPeopleId(peopleId)
            .orElseThrow(
                () -> new BusinessLogicException(PeopleProfileErrorCode.NOT_FOUND)
            );
        profile.edit(
            peopleId,
            request.introduction(),
            request.activityArea(),
            request.education(),
            request.hashtags(),
            request.techStacks(),
            request.portfolios()
        );
    }
}
