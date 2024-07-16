package es.princip.getp.domain.people.command.application;

import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.common.domain.TechStack;
import es.princip.getp.domain.people.command.domain.PeopleProfile;
import es.princip.getp.domain.people.command.domain.PeopleProfileRepository;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.people.command.domain.Portfolio;
import es.princip.getp.domain.people.command.presentation.dto.request.EditPeopleProfileRequest;
import es.princip.getp.domain.people.command.presentation.dto.request.PortfolioRequest;
import es.princip.getp.domain.people.command.presentation.dto.request.WritePeopleProfileRequest;
import es.princip.getp.domain.people.exception.PeopleErrorCode;
import es.princip.getp.domain.people.exception.PeopleProfileErrorCode;
import es.princip.getp.infra.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeopleProfileService {

    private final PeopleProfileRepository peopleProfileRepository;
    private final PeopleRepository peopleRepository;

    private List<TechStack> toTechStacks(List<String> techStacks) {
        return techStacks.stream()
            .map(TechStack::of)
            .toList();
    }

    private List<Portfolio> toPortfolios(List<PortfolioRequest> portfolios) {
        return portfolios.stream()
            .map(portfolio -> Portfolio.of(portfolio.url(), portfolio.description()))
            .toList();
    }

    private List<Hashtag> toHashtags(List<String> hashtags) {
        return hashtags.stream()
            .map(Hashtag::of)
            .toList();
    }

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
            .hashtags(toHashtags(request.hashtags()))
            .techStacks(toTechStacks(request.techStacks()))
            .portfolios(toPortfolios(request.portfolios()))
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
            toHashtags(request.hashtags()),
            toTechStacks(request.techStacks()),
            toPortfolios(request.portfolios())
        );
    }
}
