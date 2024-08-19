package es.princip.getp.domain.like.query.dao;

import es.princip.getp.common.util.DaoTest;
import es.princip.getp.common.util.DataLoader;
import es.princip.getp.domain.like.query.infra.ProjectLikeDataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ProjectLikeDaoTest extends DaoTest {

    private static final int TEST_SIZE = 5;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProjectLikeDao projectLikeDao;

    private List<DataLoader> dataLoaders;

    @BeforeEach
    void setUp() {
        dataLoaders = List.of(
            new ProjectLikeDataLoader(entityManager)
        );
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }

    @AfterEach
    void teardown() {
        dataLoaders.forEach(DataLoader::teardown);
    }

    @Test
    void 프로젝트가_받은_좋아요_수를_조회한다() {
        final Long likesCount = projectLikeDao.countByLikedId(1L);

        assertThat(likesCount).isEqualTo(TEST_SIZE);
    }
}