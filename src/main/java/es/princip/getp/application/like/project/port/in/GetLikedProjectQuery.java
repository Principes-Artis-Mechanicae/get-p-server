package es.princip.getp.application.like.project.port.in;

import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.domain.member.model.MemberId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetLikedProjectQuery {

    Page<ProjectCardResponse> getPagedCards(MemberId memberId, Pageable pageable);
}