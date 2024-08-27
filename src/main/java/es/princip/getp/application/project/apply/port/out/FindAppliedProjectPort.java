package es.princip.getp.application.project.apply.port.out;

import es.princip.getp.api.controller.project.query.dto.AppliedProjectCardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindAppliedProjectPort {

    Page<AppliedProjectCardResponse> findBy(Long memberId, Pageable pageable);
}
