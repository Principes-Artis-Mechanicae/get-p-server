package es.princip.getp.application.project.commission.port.in;

import es.princip.getp.api.controller.project.query.dto.CommissionedProjectCardResponse;
import es.princip.getp.domain.member.model.MemberId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetCommissionedProjectQuery {

    /**
     * 의뢰자가 의뢰한 프로젝트 목록을 페이징하여 조회한다.
     *
     * @param pageable 페이지 정보
     * @param memberId 의뢰자의 회원 ID
     * @return 의뢰자가 의뢰한 프로젝트 목록 페이지
     */
    // TODO: 검색 조건이 아직 명확하지 않음. 추후 변경 필요
    Page<CommissionedProjectCardResponse> getPagedCards(MemberId memberId, Boolean cancelled, Pageable pageable);
}