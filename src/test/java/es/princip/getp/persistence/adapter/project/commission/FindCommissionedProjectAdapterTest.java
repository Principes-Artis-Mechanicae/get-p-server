package es.princip.getp.persistence.adapter.project.commission;

import es.princip.getp.api.controller.project.query.dto.CommissionedProjectCardResponse;
import es.princip.getp.common.util.DataLoader;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import es.princip.getp.persistence.adapter.PersistenceAdapterTest;
import es.princip.getp.persistence.adapter.client.ClientDataLoader;
import es.princip.getp.persistence.adapter.project.ProjectPersistenceMapper;
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

class FindCommissionedProjectAdapterTest extends PersistenceAdapterTest {

    private static final int PAGE_SIZE = 10;
    private static final int TEST_SIZE = 20;

    @PersistenceContext private EntityManager entityManager;
    @Autowired private FindCommissionedProjectAdapter adapter;
    @Autowired private ProjectPersistenceMapper mapper;

    private List<DataLoader> dataLoaders;

    @BeforeEach
    void setUp() {
        dataLoaders = List.of(
            new ClientDataLoader(entityManager),
            new ProjectDataLoader(mapper, entityManager)
        );
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }

    @AfterEach
    void teardown() {
        dataLoaders.forEach(DataLoader::teardown);
    }

    @Nested
    class 의뢰한_프로젝트_목록_페이지를_조회한다 {

        final Pageable pageable = PageRequest.of(0, PAGE_SIZE);

        @Test
        void 만료된_프로젝트도_보는_경우() {
            final Page<CommissionedProjectCardResponse> response = adapter.findBy(
                1L,
                true,
                pageable
            );

            assertThat(response.getContent()).allSatisfy(content -> {
                assertThat(content).usingRecursiveComparison().isNotNull();
            });
        }

        @Test
        void 만료된_프로젝트는_보지_않는_경우() {
            final Page<CommissionedProjectCardResponse> response = adapter.findBy(
                1L,
                false,
                pageable
            );

            assertThat(response.getContent()).allSatisfy(content -> {
                assertThat(content).usingRecursiveComparison().isNotNull();
                assertThat(content.status()).isNotEqualTo(ProjectStatus.CANCELLED);
            });
        }
    }
}