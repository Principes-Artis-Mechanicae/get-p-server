package es.princip.getp.domain.like.query.dao;

import es.princip.getp.common.util.DaoTest;
import es.princip.getp.common.util.DataLoader;
import es.princip.getp.domain.like.query.infra.PeopleLikeDataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class PeopleLikeDaoTest extends DaoTest {

    private static final int TEST_SIZE = 5;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PeopleLikeDao peopleLikeDao;

    private List<DataLoader> dataLoaders;

    @BeforeEach
    void setUp() {
        dataLoaders = List.of(
            new PeopleLikeDataLoader(entityManager)
        );
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }

    @AfterEach
    void teardown() {
        dataLoaders.forEach(DataLoader::teardown);
    }

    @Test
    void 피플이_받은_좋아요_수를_조회한다() {
        assertThat(peopleLikeDao.countByLikedId(1L)).isEqualTo(TEST_SIZE);
    }

    @Test
    void 여러명의_피플이_받은_좋아요_수를_조회한다() {
        final Long[] peopleIds = LongStream.rangeClosed(1, TEST_SIZE)
            .boxed()
            .toArray(Long[]::new);
        final Map<Long, Long> likesCounts = peopleLikeDao.countByLikedIds(peopleIds);

        assertThat(likesCounts).hasSize(peopleIds.length)
            .containsOnlyKeys(peopleIds)
            .containsValues(Long.valueOf(TEST_SIZE));
    }
}