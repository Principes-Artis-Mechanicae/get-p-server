package es.princip.getp.domain.like.query.dao;

import es.princip.getp.domain.like.query.infra.PeopleLikeDaoConfig;
import es.princip.getp.infra.support.DaoTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.Map;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Import(PeopleLikeDaoConfig.class)
public class ZeroTestSizePeopleLikeDaoTest extends DaoTest {

    public ZeroTestSizePeopleLikeDaoTest() {
        super(0);
    }

    @Autowired
    private PeopleLikeDao peopleLikeDao;

    @Test
    void 피플_ID에_대해_피플이_받은_좋아요_수를_조회한다() {
        assertThat(peopleLikeDao.countByLikedId(1L)).isEqualTo(0);
    }

    @Test
    void 여러개의_피플_ID에_대해_피플이_받은_좋아요_수를_조회한다() {
        final Long[] peopleIds = LongStream.rangeClosed(1, 10)
            .boxed()
            .toArray(Long[]::new);
        final Map<Long, Long> likesCounts = peopleLikeDao.countByLikedIds(peopleIds);
        assertThat(likesCounts).hasSize(peopleIds.length)
            .containsOnlyKeys(peopleIds)
            .containsValues(Long.valueOf(0));
    }
}