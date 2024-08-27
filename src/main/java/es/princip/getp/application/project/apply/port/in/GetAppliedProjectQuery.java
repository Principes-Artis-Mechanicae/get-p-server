package es.princip.getp.application.project.apply.port.in;

import es.princip.getp.api.controller.project.query.dto.AppliedProjectCardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetAppliedProjectQuery {

    Page<AppliedProjectCardResponse> getPagedCards(Long memberId, Pageable pageable);
}
