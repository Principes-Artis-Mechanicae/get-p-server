package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.persistence.support.DataLoader;
import es.princip.getp.persistence.support.PersistenceAdapterTest;
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

class CountProjectApplicationAdapterTest extends PersistenceAdapterTest {

    private static final int TEST_SIZE = 10;

    @PersistenceContext private EntityManager entityManager;
    @Autowired private CountProjectApplicationAdapter adapter;

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
    void 각_프로젝트마다_지원자_수를_구한다() {
        final ProjectId[] projectIds = LongStream.rangeClosed(1, TEST_SIZE)
            .boxed()
            .map(ProjectId::new)
            .toArray(ProjectId[]::new);
        final Map<ProjectId, Long> result = adapter.countBy(projectIds);

        assertThat(result).hasSize(TEST_SIZE);
        assertThat(result.values()).allMatch(value -> value == 1L);
    }

    @Test
    void 프로젝트의_지원자_수를_구한다() {
        final ProjectId projectId = new ProjectId(1L);
        final Long result = adapter.countBy(projectId);

        assertThat(result).isEqualTo(1L);
    }
}