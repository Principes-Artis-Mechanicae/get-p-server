package es.princip.getp.domain.project.query.dao;

import es.princip.getp.domain.project.query.dto.ProjectCardResponse;
import es.princip.getp.domain.project.query.dto.ProjectDetailResponse;
import es.princip.getp.domain.project.query.infra.ProjectDaoConfig;
import es.princip.getp.infra.support.DaoTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Import(ProjectDaoConfig.class)
class ProjectDaoTest extends DaoTest {

    public ProjectDaoTest() {
        super(100);
    }

    @Autowired
    private ProjectDao projectDao;

    @Test
    void findPagedProjectCard() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProjectCardResponse> response = projectDao.findPagedProjectCard(pageable);

        log.info("response: {}", response.getContent());

        assertThat(response.getContent()).isNotEmpty();
        assertThat(response.getTotalElements()).isEqualTo(TEST_SIZE);
        assertThat(response.getNumberOfElements()).isEqualTo(10);
    }

    @Test
    void findProjectDetailById() {
        ProjectDetailResponse response = projectDao.findProjectDetailById(1L);

        log.info("response: {}", response);

        assertThat(response).isNotNull();
    }
}