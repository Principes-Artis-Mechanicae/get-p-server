package es.princip.getp.application.like.project.port.out;

import es.princip.getp.application.project.commission.dto.response.ProjectCardResponse;
import es.princip.getp.domain.member.model.MemberId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindLikedProjectPort {

    Page<ProjectCardResponse> findBy(MemberId memberId, Pageable pageable);
}
