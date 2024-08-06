package es.princip.getp.domain.like.query.dao;

import es.princip.getp.domain.like.query.infra.ProjectLikeDaoConfig;
import es.princip.getp.infra.support.DaoTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@Import(ProjectLikeDaoConfig.class)
class ProjectLikeDaoTest extends DaoTest {

    public ProjectLikeDaoTest() {
        super(10);
    }

    @Autowired
    private LikeDao projectLikeDao;

    @Test
    void countByLikedId() {
        final Long likesCount = projectLikeDao.countByLikedId(1L);

        assertThat(likesCount).isEqualTo(TEST_SIZE);
    }
}