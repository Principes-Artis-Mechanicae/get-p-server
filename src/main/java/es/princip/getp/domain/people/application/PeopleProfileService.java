package es.princip.getp.domain.people.application;

import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleProfile;
import es.princip.getp.domain.people.domain.PeopleRepository;
import es.princip.getp.domain.people.presentation.dto.request.EditPeopleProfileRequest;
import es.princip.getp.domain.people.presentation.dto.request.WritePeopleProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeopleProfileService {

    private final PeopleRepository peopleRepository;

    @Transactional
    public void writeProfile(Long memberId, WritePeopleProfileRequest request) {
        People people = peopleRepository.findByMemberId(memberId).orElseThrow();
        PeopleProfile profile = PeopleProfile.builder()
            .introduction(request.introduction())
            .activityArea(request.activityArea())
            .education(request.education())
            .hashtags(request.hashtags())
            .techStacks(request.techStacks())
            .portfolios(request.portfolios())
            .build();
        people.writeProfile(memberId, profile);
    }

    @Transactional
    public void editProfile(Long memberId, EditPeopleProfileRequest request) {
        People people = peopleRepository.findByMemberId(memberId).orElseThrow();
        PeopleProfile profile = PeopleProfile.builder()
            .introduction(request.introduction())
            .activityArea(request.activityArea())
            .education(request.education())
            .hashtags(request.hashtags())
            .techStacks(request.techStacks())
            .portfolios(request.portfolios())
            .build();
        people.editProfile(memberId, profile);
    }
}
