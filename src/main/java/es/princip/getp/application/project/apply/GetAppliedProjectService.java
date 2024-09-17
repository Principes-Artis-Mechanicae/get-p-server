package es.princip.getp.application.project.apply;

import es.princip.getp.api.controller.project.query.dto.AppliedProjectCardResponse;
import es.princip.getp.application.project.apply.port.in.GetAppliedProjectQuery;
import es.princip.getp.application.project.apply.port.out.FindAppliedProjectPort;
import es.princip.getp.domain.member.model.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetAppliedProjectService implements GetAppliedProjectQuery {

    private final FindAppliedProjectPort findAppliedProjectPort;

    @Override
    public Page<AppliedProjectCardResponse> getPagedCards(final MemberId memberId, final Pageable pageable) {
        return findAppliedProjectPort.findBy(memberId, pageable);
    }
}
