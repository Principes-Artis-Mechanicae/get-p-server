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
import es.princip.getp.infra.exception.EntityAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
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

    private List<TechStack> toTechStacks(final List<String> techStacks) {
        return techStacks.stream()
            .map(TechStack::from)
            .toList();
    }

    private List<Portfolio> toPortfolios(final List<PortfolioRequest> portfolios) {
        return portfolios.stream()
            .map(portfolio -> Portfolio.of(portfolio.description(), portfolio.url()))
            .toList();
    }

    private List<Hashtag> toHashtags(final List<String> hashtags) {
        return hashtags.stream()
            .map(Hashtag::from)
            .toList();
    }

    /**
     * 피플 프로필을 작성한다.
     *
     * @param memberId 회원 ID
     * @param request 피플 프로필 작성 요청
     * @throws EntityNotFoundException 등록된 피플 정보가 없는 경우
     * @throws EntityAlreadyExistsException 이미 등록된 피플 프로필이 존재하는 경우
     */
    @Transactional
    public void writeProfile(final Long memberId, final WritePeopleProfileRequest request) {
        final Long peopleId = peopleRepository.findByMemberId(memberId)
            .orElseThrow(
                () -> new EntityNotFoundException("등록된 피플 정보가 없습니다.")
            )
            .getPeopleId();

        if (peopleProfileRepository.existsByPeopleId(peopleId)) {
            throw new EntityAlreadyExistsException("이미 등록된 피플 프로필이 존재합니다.");
        }

        final PeopleProfile profile = PeopleProfile.builder()
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

    /**
     * 피플 프로필을 수정한다.
     *
     * @param memberId 회원 ID
     * @param request 피플 프로필 수정 요청
     * @throws EntityNotFoundException 등록된 피플 정보가 없는 경우
     * @throws EntityNotFoundException 등록된 피플 프로필이 없는 경우
     */
    @Transactional
    public void editProfile(final Long memberId, final EditPeopleProfileRequest request) {
        final Long peopleId = peopleRepository.findByMemberId(memberId)
            .orElseThrow(
                () -> new EntityNotFoundException("등록된 피플 정보가 없습니다.")
            )
            .getPeopleId();

        final PeopleProfile profile = peopleProfileRepository.findByPeopleId(peopleId)
            .orElseThrow(
                () -> new EntityNotFoundException("등록된 피플 프로필이 없습니다.")
            );

        profile.edit(
            request.introduction(),
            request.activityArea(),
            request.education(),
            toHashtags(request.hashtags()),
            toTechStacks(request.techStacks()),
            toPortfolios(request.portfolios())
        );
    }
}
