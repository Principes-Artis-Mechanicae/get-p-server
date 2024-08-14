package es.princip.getp.domain.project.query.dao;

import es.princip.getp.domain.people.query.dto.people.DetailPeopleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectApplicantDao {

    Page<DetailPeopleResponse> findPagedApplicantByProjectId(Long projectId, Pageable pageable);
}
