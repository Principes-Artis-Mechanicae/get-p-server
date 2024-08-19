package es.princip.getp.domain.project.query.dao;

import es.princip.getp.common.util.DaoTest;
import es.princip.getp.common.util.DataLoader;
import es.princip.getp.domain.client.query.infra.ClientDataLoader;
import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.domain.project.query.infra.ProjectDataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ProjectDaoTest extends DaoTest {

    private static final int TEST_SIZE = 20;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProjectDao projectDao;

    private List<DataLoader> dataLoaders;

    @BeforeEach
    void setUp() {
        dataLoaders = List.of(
            new ClientDataLoader(entityManager),
            new ProjectDataLoader(entityManager)
        );
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }

    @AfterEach
    void teardown() {
        dataLoaders.forEach(DataLoader::teardown);
    }

    @Test
    void findPagedProjectCard() {
        final int pageSize = 10;
        final Pageable pageable = PageRequest.of(0, pageSize);
        final Page<ProjectCardResponse> response = projectDao.findPagedProjectCard(pageable);

        assertThat(response.getContent()).isNotEmpty();
        assertThat(response.getTotalElements()).isGreaterThan(pageSize);
        assertThat(response.getNumberOfElements()).isEqualTo(pageSize);
    }

    @Test
    void findProjectDetailById() {
        final ProjectDetailResponse response = projectDao.findProjectDetailById(1L);

        assertThat(response).isNotNull();
    }
}