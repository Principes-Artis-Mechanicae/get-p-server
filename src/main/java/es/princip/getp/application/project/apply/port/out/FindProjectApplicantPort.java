package es.princip.getp.application.project.apply.port.out;

import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindProjectApplicantPort {

    Page<DetailPeopleResponse> findBy(Long projectId, Pageable pageable);
}
