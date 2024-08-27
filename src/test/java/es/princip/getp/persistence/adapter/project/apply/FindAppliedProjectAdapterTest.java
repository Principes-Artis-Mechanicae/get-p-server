package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.api.controller.project.query.dto.AppliedProjectCardResponse;
import es.princip.getp.common.util.DataLoader;
import es.princip.getp.domain.people.query.infra.PeopleDataLoader;
import es.princip.getp.persistence.adapter.PersistenceAdapterTest;
import es.princip.getp.persistence.adapter.project.ProjectPersistenceMapper;
import es.princip.getp.persistence.adapter.project.commission.ProjectDataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FindAppliedProjectAdapterTest extends PersistenceAdapterTest {

    private static final int TEST_SIZE = 20;
    private static final int PAGE_SIZE = 10;

    @PersistenceContext private EntityManager entityManager;
    @Autowired private FindAppliedProjectAdapter adapter;
    @Autowired private ProjectApplicationPersistenceMapper applicationMapper;
    @Autowired private ProjectPersistenceMapper projectMapper;

    private List<DataLoader> dataLoaders;

    @BeforeEach
    void setUp() {
        dataLoaders = List.of(
            new PeopleDataLoader(entityManager),
            new ProjectDataLoader(projectMapper, entityManager),
            new ProjectApplicationDataLoader(applicationMapper, entityManager)
        );
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }

    @AfterEach
    void teardown() {
        dataLoaders.forEach(DataLoader::teardown);
    }


    final Pageable pageable = PageRequest.of(0, PAGE_SIZE);

    @Test
    void 지원한_프로젝트_목록을_조회한다() {
        final Page<AppliedProjectCardResponse> response = adapter.findBy(1L, pageable);

        assertThat(response.getContent()).allSatisfy(content ->
            assertThat(content).usingRecursiveComparison().isNotNull()
        );
        assertThat(response.getNumberOfElements()).isGreaterThan(0);
    }
}
