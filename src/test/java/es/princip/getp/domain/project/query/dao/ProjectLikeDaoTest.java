package es.princip.getp.domain.project.query.dao;

import es.princip.getp.domain.project.query.infra.ProjectLikeDaoConfig;
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
    private ProjectLikeDao projectLikeDao;

    @Test
    void countByProjectId() {
        final Long likesCount = projectLikeDao.countByProjectId(1L);

        assertThat(likesCount).isEqualTo(TEST_SIZE);
    }
}