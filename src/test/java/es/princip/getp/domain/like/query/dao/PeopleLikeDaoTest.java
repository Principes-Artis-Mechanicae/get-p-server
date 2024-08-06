package es.princip.getp.domain.like.query.dao;

import es.princip.getp.domain.like.query.infra.PeopleLikeDaoConfig;
import es.princip.getp.infra.support.DaoTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.Map;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Import(PeopleLikeDaoConfig.class)
public class PeopleLikeDaoTest extends DaoTest {

    public PeopleLikeDaoTest() {
        super(10);
    }

    @Autowired
    private PeopleLikeDao peopleLikeDao;


    @Test
    @DisplayName("피플이 받은 좋아요 수를 조회한다.")
    void countByLikedId() {
        assertThat(peopleLikeDao.countByLikedId(1L)).isEqualTo(TEST_SIZE);
    }

    @Test
    @DisplayName("피플이 받은 좋아요 수를 조회한다.")
    void countByLikedIds() {
        final Long[] peopleIds = LongStream.rangeClosed(1, TEST_SIZE)
            .boxed()
            .toArray(Long[]::new);
        final Map<Long, Long> likesCounts = peopleLikeDao.countByLikedIds(peopleIds);
        assertThat(likesCounts).hasSize(peopleIds.length)
            .containsOnlyKeys(peopleIds)
            .containsValues(Long.valueOf(TEST_SIZE));
    }
}