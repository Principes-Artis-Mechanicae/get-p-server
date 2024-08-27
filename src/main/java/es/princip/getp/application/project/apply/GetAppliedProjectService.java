package es.princip.getp.application.project.apply;

import es.princip.getp.api.controller.project.query.dto.AppliedProjectCardResponse;
import es.princip.getp.application.project.apply.port.in.GetAppliedProjectQuery;
import es.princip.getp.application.project.apply.port.out.FindAppliedProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAppliedProjectService implements GetAppliedProjectQuery {

    private final FindAppliedProjectPort findAppliedProjectPort;

    @Override
    public Page<AppliedProjectCardResponse> getPagedCards(final Long memberId, final Pageable pageable) {
        return findAppliedProjectPort.findBy(memberId, pageable);
    }
}
