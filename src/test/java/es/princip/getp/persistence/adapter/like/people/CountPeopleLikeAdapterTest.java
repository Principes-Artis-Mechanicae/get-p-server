package es.princip.getp.persistence.adapter.like.people;

import es.princip.getp.application.like.port.out.people.CountPeopleLikePort;
import es.princip.getp.common.util.DaoTest;
import es.princip.getp.common.util.DataLoader;
import es.princip.getp.persistence.adapter.like.command.PeopleLikePersistenceMapper;
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
public class CountPeopleLikeAdapterTest extends DaoTest {

    private static final int TEST_SIZE = 5;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CountPeopleLikePort countPeopleLikePort;

    @Autowired
    private PeopleLikePersistenceMapper peopleLikeMapper;

    private List<DataLoader> dataLoaders;

    @BeforeEach
    void setUp() {
        dataLoaders = List.of(
            new PeopleLikeDataLoader(entityManager, peopleLikeMapper)
        );
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }

    @AfterEach
    void teardown() {
        dataLoaders.forEach(DataLoader::teardown);
    }

    @Test
    void 피플이_받은_좋아요_수를_조회한다() {
        assertThat(countPeopleLikePort.countByPeopleId(1L)).isEqualTo(TEST_SIZE);
    }

    @Test
    void 여러명의_피플이_받은_좋아요_수를_조회한다() {
        final Long[] peopleIds = LongStream.rangeClosed(1, TEST_SIZE)
            .boxed()
            .toArray(Long[]::new);
        final Map<Long, Long> likesCounts = countPeopleLikePort.countByPeopleIds(peopleIds);

        assertThat(likesCounts).hasSize(peopleIds.length)
            .containsOnlyKeys(peopleIds)
            .containsValues(Long.valueOf(TEST_SIZE));
    }
}