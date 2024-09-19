package es.princip.getp.persistence.adapter.like.project;

import es.princip.getp.application.like.project.port.out.CountProjectLikePort;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.persistence.support.DataLoader;
import es.princip.getp.persistence.support.PersistenceAdapterTest;
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
public class CountProjectLikeAdapterTest extends PersistenceAdapterTest {

    @PersistenceContext private EntityManager entityManager;
    @Autowired private CountProjectLikePort countProjectLikePort;
    @Autowired private ProjectLikePersistenceMapper projectLikeMapper;

    private static final int TEST_SIZE = 5;
    private List<DataLoader> dataLoaders;

    @BeforeEach
    void setUp() {
        dataLoaders = List.of(
            new ProjectLikeDataLoader(entityManager, projectLikeMapper)
        );
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }

    @AfterEach
    void teardown() {
        dataLoaders.forEach(DataLoader::teardown);
    }

    @Test
    void 프로젝트가_받은_좋아요_수를_조회한다() {
        final Long likesCount = countProjectLikePort.countBy(new ProjectId(1L));

        assertThat(likesCount).isEqualTo(TEST_SIZE);
    }
}