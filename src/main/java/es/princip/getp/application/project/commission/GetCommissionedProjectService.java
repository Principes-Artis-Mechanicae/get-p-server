package es.princip.getp.application.project.commission;

import es.princip.getp.api.controller.project.query.dto.CommissionedProjectCardResponse;
import es.princip.getp.application.project.commission.port.in.GetCommissionedProjectQuery;
import es.princip.getp.application.project.commission.port.out.FindCommissionedProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetCommissionedProjectService implements GetCommissionedProjectQuery {

    private final FindCommissionedProjectPort findCommissionedProjectPort;

    @Override
    public Page<CommissionedProjectCardResponse> getPagedCards(
        final Long memberId,
        final Boolean cancelled,
        final Pageable pageable
    ) {
        return findCommissionedProjectPort.findBy(memberId, cancelled, pageable);
    }
}
