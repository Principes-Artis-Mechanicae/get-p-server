package es.princip.getp.domain.project.query.dao;

import es.princip.getp.domain.project.query.dto.CardProjectResponse;
import es.princip.getp.domain.project.query.dto.DetailProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProjectDao {

    Page<CardProjectResponse> findCardProjectPage(Pageable pageable);

    Optional<DetailProjectResponse> findDetailProjectById(Long projectId);
}