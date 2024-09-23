package es.princip.getp.application.like.project.service;

import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.application.like.project.port.in.GetLikedProjectQuery;
import es.princip.getp.application.like.project.port.out.FindLikedProjectPort;
import es.princip.getp.domain.member.model.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetLikedProjectService implements GetLikedProjectQuery {

    private final FindLikedProjectPort findLikedProjectPort;

    @Override
    public Page<ProjectCardResponse> getPagedCards(final MemberId memberId, final Pageable pageable) {
        return findLikedProjectPort.findBy(memberId, pageable);
    }
}
