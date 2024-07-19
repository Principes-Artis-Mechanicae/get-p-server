package es.princip.getp.domain.project.query.dao;

import es.princip.getp.domain.project.query.dto.CardProjectResponse;
import es.princip.getp.domain.project.query.dto.DetailProjectResponse;
import es.princip.getp.domain.project.query.infra.ProjectDaoConfig;
import es.princip.getp.infra.annotation.DataTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static es.princip.getp.infra.config.DataLoaderConfig.TEST_SIZE;
import static org.assertj.core.api.Assertions.assertThat;

@DataTest
@Import(ProjectDaoConfig.class)
@Slf4j
class ProjectDaoImplTest {

    @Autowired
    private ProjectDao projectDao;

    @Test
    void findCardProjectPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CardProjectResponse> response = projectDao.findCardProjectPage(pageable);

        log.info("response: {}", response.getContent());

        assertThat(response.getContent()).isNotEmpty();
        assertThat(response.getTotalElements()).isEqualTo(TEST_SIZE);
        assertThat(response.getNumberOfElements()).isEqualTo(10);
    }

    @Test
    void findDetailProjectById() {
        DetailProjectResponse response = projectDao.findDetailProjectById(1L);

        log.info("response: {}", response);

        assertThat(response).isNotNull();
    }
}