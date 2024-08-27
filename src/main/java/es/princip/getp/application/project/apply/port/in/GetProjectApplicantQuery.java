package es.princip.getp.application.project.apply.port.in;

import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.domain.member.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetProjectApplicantQuery {

    Page<DetailPeopleResponse> getPagedDetails(Long projectId, Member member, Pageable pageable);
}
