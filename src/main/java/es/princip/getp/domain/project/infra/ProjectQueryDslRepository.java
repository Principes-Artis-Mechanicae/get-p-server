package es.princip.getp.domain.project.infra;

import es.princip.getp.domain.project.dto.response.CardProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectQueryDslRepository {
    Page<CardProjectResponse> findToCardProjectResponse(Pageable pageable);
}