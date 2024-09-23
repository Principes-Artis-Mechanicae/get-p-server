package es.princip.getp.persistence.adapter.like.project;

import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.persistence.adapter.member.MemberDataLoader;
import es.princip.getp.persistence.adapter.project.ProjectPersistenceMapper;
import es.princip.getp.persistence.adapter.project.commission.ProjectDataLoader;
import es.princip.getp.persistence.support.DataLoader;
import es.princip.getp.persistence.support.PersistenceAdapterTest;
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

class FindLikedProjectAdapterTest extends PersistenceAdapterTest {

    private static final int PAGE_SIZE = 10;
    private static final int TEST_SIZE = 20;

    @PersistenceContext private EntityManager entityManager;
    @Autowired private FindLikedProjectAdapter adapter;
    @Autowired private ProjectPersistenceMapper mapper;

    private List<DataLoader> dataLoaders;

    @BeforeEach
    void setUp() {
        dataLoaders = List.of(
            new MemberDataLoader(entityManager),
            new ProjectLikeDataLoader(entityManager),
            new ProjectDataLoader(mapper, entityManager)
        );
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }

    @AfterEach
    void teardown() {
        dataLoaders.forEach(DataLoader::teardown);
    }

    private final MemberId memberId = new MemberId(1L);
    private final Pageable pageable = PageRequest.of(0, PAGE_SIZE);

    @Test
    void 좋아요한_프로젝트_목록_페이지를_조회한다() {
        final Page<ProjectCardResponse> response = adapter.findBy(memberId, pageable);

        assertThat(response.getContent()).allSatisfy(content -> {
            assertThat(content).usingRecursiveComparison().isNotNull();
        });
        assertThat(response.getNumberOfElements()).isEqualTo(PAGE_SIZE);
        assertThat(response.getTotalElements()).isEqualTo(TEST_SIZE);
        assertThat(response.getTotalPages()).isEqualTo(TEST_SIZE / PAGE_SIZE);
    }
}