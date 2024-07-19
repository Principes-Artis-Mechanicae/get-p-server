package es.princip.getp.domain.project.query.dao;

import es.princip.getp.domain.project.query.dto.CardProjectResponse;
import es.princip.getp.domain.project.query.dto.DetailProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectDao {

    Page<CardProjectResponse> findCardProjectPage(Pageable pageable);

    DetailProjectResponse findDetailProjectById(Long projectId);
}