package es.princip.getp.domain.project.query.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.princip.getp.domain.project.query.dto.AppliedProjectCardResponse;

public interface AppliedProjectDao {

    Page<AppliedProjectCardResponse> findPagedMyAppliedProjects(Pageable pageable, Long memberId);
}
