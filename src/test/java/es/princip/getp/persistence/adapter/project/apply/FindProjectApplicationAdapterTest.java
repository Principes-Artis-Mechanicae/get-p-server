package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.common.util.DataLoader;
import es.princip.getp.persistence.adapter.PersistenceAdapterTest;
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

class FindProjectApplicationAdapterTest extends PersistenceAdapterTest {

    private static final int TEST_SIZE = 10;

    @PersistenceContext private EntityManager entityManager;
    @Autowired private ProjectApplicationPersistenceMapper applicationMapper;
    @Autowired private FindProjectApplicationAdapter adapter;

    private List<DataLoader> dataLoaders;

    @BeforeEach
    void setUp() {
        dataLoaders = List.of(
            new ProjectApplicationDataLoader(applicationMapper, entityManager)
        );
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }

    @AfterEach
    void teardown() {
        dataLoaders.forEach(DataLoader::teardown);
    }

    @Test
    void 각_프로젝트마다_지원자_수를_구한다() {
        final Long[] projectIds = LongStream.rangeClosed(1, TEST_SIZE)
            .boxed()
            .toArray(Long[]::new);
        final Map<Long, Long> result = adapter.countByProjectIds(projectIds);

        assertThat(result).hasSize(TEST_SIZE);
        assertThat(result.values()).allMatch(value -> value == TEST_SIZE);
    }
}