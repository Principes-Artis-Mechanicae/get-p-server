package es.princip.getp.application.project.apply.port.in;

import es.princip.getp.api.controller.project.query.dto.AppliedProjectCardResponse;
import es.princip.getp.domain.member.model.MemberId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetAppliedProjectQuery {

    Page<AppliedProjectCardResponse> getPagedCards(MemberId memberId, Pageable pageable);
}
