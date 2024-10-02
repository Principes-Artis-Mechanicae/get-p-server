package es.princip.getp.application.project.commission;

import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.application.project.commission.command.GetProjectCommand;
import es.princip.getp.application.project.commission.command.ProjectSearchFilter;
import es.princip.getp.application.project.commission.port.in.GetProjectQuery;
import es.princip.getp.application.project.commission.port.out.FindProjectPort;
import es.princip.getp.application.resolver.MosaicResolver;
import es.princip.getp.application.support.ApplicationSupport;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;

import es.princip.getp.domain.project.commission.model.ProjectId;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetProjectService extends ApplicationSupport implements GetProjectQuery {

    private final FindProjectPort findProjectPort;

    private final MosaicResolver mosaicResolver;
    
    private boolean doesFilterRequireLogin(final ProjectSearchFilter filter) {
        return (filter.isApplied() || filter.isLiked() || filter.isCommissioned());
    }

    private boolean isFilterAuthorized(final ProjectSearchFilter filter, final Member member) {
        return (filter.isApplied() && member.isPeople()) ||
            (filter.isLiked() && member.isPeople()) ||
            (filter.isCommissioned() && member.isClient());
    }

    private boolean isMemberAuthenticated(final Member member) {
        return member != null;
    }

    private void validateFilter(final ProjectSearchFilter filter, final Member member) {
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
    public Page<ProjectCardResponse> getPagedCards(final GetProjectCommand command) {
        final ProjectSearchFilter filter = command.filter();
        validateFilter(filter, command.member());
        final Pageable pageable = command.pageable();
        final MemberId memberId = Optional.ofNullable(command.member())
            .map(Member::getId)
            .orElse(null);
        return findProjectPort.findBy(pageable, filter, memberId);
    }

    @Override
    public ProjectDetailResponse getDetailBy(final Member member, final ProjectId projectId) {
        ProjectDetailResponse response = findProjectPort.findBy(member, projectId);
        if (isNotLogined(member)) {
            return mosaicResolver.resolve(response);
        }
        return response;
    }
}
