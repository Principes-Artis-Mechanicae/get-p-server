package es.princip.getp.domain.project.query.dao;

import es.princip.getp.domain.project.query.dto.ProjectCardResponse;
import es.princip.getp.domain.project.query.dto.ProjectDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectDao {

    Page<ProjectCardResponse> findPagedProjectCard(Pageable pageable);

    boolean existsByProjectIdAndMemberId(Long projectId, Long memberId);

    ProjectDetailResponse findProjectDetailById(Long projectId);
}