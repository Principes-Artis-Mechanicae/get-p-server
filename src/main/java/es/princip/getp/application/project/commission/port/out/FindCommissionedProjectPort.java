package es.princip.getp.application.project.commission.port.out;

import es.princip.getp.api.controller.project.query.dto.CommissionedProjectCardResponse;
import es.princip.getp.domain.member.model.MemberId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindCommissionedProjectPort {

    Page<CommissionedProjectCardResponse> findBy(MemberId memberId, Boolean cancelled, Pageable pageable);
}
