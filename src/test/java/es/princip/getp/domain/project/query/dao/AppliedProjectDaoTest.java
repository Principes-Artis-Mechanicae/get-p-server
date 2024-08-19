package es.princip.getp.domain.project.query.dao;

import es.princip.getp.common.util.DaoTest;
import es.princip.getp.common.util.DataLoader;
import es.princip.getp.domain.people.query.infra.PeopleDataLoader;
import es.princip.getp.api.controller.project.query.dto.AppliedProjectCardResponse;
import es.princip.getp.domain.project.query.infra.ProjectApplicationDataLoader;
import es.princip.getp.domain.project.query.infra.ProjectDataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AppliedProjectDaoTest extends DaoTest {

    private static final int TEST_SIZE = 20;
    private static final int PAGE_SIZE = 10;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AppliedProjectDao appliedProjectDao;

    private List<DataLoader> dataLoaders;

    @BeforeEach
    void setUp() {
        dataLoaders = List.of(
            new PeopleDataLoader(entityManager),
            new ProjectDataLoader(entityManager),
            new ProjectApplicationDataLoader(entityManager)
        );
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }

    @AfterEach
    void teardown() {
        dataLoaders.forEach(DataLoader::teardown);
    }

    @Nested
    class 지원한_프로젝트_목록_조회 {

        final Pageable pageable = PageRequest.of(0, PAGE_SIZE);

        @Test
        void 프로젝트_목록_조회() {
            final Page<AppliedProjectCardResponse> response = appliedProjectDao.findPagedMyAppliedProjects(
                pageable,
                1L
            );

            assertThat(response.getContent()).allSatisfy(content -> {
                assertThat(content).usingRecursiveComparison().isNotNull();
            });
            assertThat(response.getNumberOfElements()).isGreaterThan(0);
        }
    }
}
