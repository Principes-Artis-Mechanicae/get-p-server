package es.princip.getp.domain.project.query.dao;

import es.princip.getp.common.util.DaoTest;
import es.princip.getp.common.util.DataLoader;
import es.princip.getp.domain.project.query.infra.ProjectApplicationDataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectApplicationDaoTest extends DaoTest {

    private static final int TEST_SIZE = 10;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProjectApplicationDao projectApplicationDao;

    private List<DataLoader> dataLoaders;

    @BeforeEach
    void setUp() {
        dataLoaders = List.of(
            new ProjectApplicationDataLoader(entityManager)
        );
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }

    @AfterEach
    void teardown() {
        dataLoaders.forEach(DataLoader::teardown);
    }

    @Test
    void countByProjectIds() {
        final Long[] projectIds = LongStream.rangeClosed(1, TEST_SIZE)
            .boxed()
            .toArray(Long[]::new);
        final Map<Long, Long> result = projectApplicationDao.countByProjectIds(projectIds);

        assertThat(result).hasSize(TEST_SIZE);
        assertThat(result.values()).allMatch(value -> value == TEST_SIZE);
    }
}