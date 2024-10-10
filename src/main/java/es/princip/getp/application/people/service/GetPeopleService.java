package es.princip.getp.application.people.service;

import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PeopleDetailResponse;
import es.princip.getp.application.people.command.GetPeopleCommand;
import es.princip.getp.application.people.command.PeopleSearchFilter;
import es.princip.getp.application.people.port.in.GetPeopleQuery;
import es.princip.getp.application.people.port.out.FindPeoplePort;
import es.princip.getp.application.support.MosaicFactory;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static es.princip.getp.application.support.ApplicationQueryUtil.isNotLogined;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetPeopleService implements GetPeopleQuery {

    private final FindPeoplePort findPeoplePort;
    private final MosaicFactory mosaicFactory;

    private boolean doesFilterRequireLogin(final PeopleSearchFilter filter) {
        return filter.isLiked();
    }

    private boolean isFilterAuthorized(final PeopleSearchFilter filter, final Member member) {
        return (filter.isLiked() && member.isClient());
    }

    private boolean isMemberAuthenticated(final Member member) {
        return member != null;
    }

    private void validateFilter(final PeopleSearchFilter filter, final Member member) {
        if (!doesFilterRequireLogin(filter)) {
            return ;
        }
        if (!isMemberAuthenticated(member)) {
            throw new AuthenticationCredentialsNotFoundException("해당 필터를 사용하려면 로그인이 필요합니다.");
        }
        if (!isFilterAuthorized(filter, member)) {
            throw new AccessDeniedException("사용자가 사용할 수 없는 필터입니다.");
        }
    }

    @Override
    public Page<CardPeopleResponse> getPagedCards(final GetPeopleCommand command) {
        final PeopleSearchFilter filter = command.filter();
        validateFilter(filter, command.member());
        final Pageable pageable = command.pageable();
        final MemberId memberId = Optional.ofNullable(command.member())
            .map(Member::getId)
            .orElse(null);
        return findPeoplePort.findCardBy(pageable, filter, memberId);
    }

    @Override
    public PeopleDetailResponse getDetailBy(final Member member, final PeopleId peopleId) {
        final PeopleDetailResponse response = findPeoplePort.findDetailBy(member, peopleId);
        if (isNotLogined(member)) {
            return mosaicFactory.mosaic(response);
        }
        return response;
    }
}