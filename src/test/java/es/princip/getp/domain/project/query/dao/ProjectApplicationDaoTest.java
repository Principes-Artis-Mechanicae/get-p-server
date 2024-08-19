package es.princip.getp.domain.project.query.dao;

import es.princip.getp.domain.project.query.infra.ProjectApplicationDaoConfig;
import es.princip.getp.infra.support.DaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.Map;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@Import(ProjectApplicationDaoConfig.class)
class ProjectApplicationDaoTest extends DaoTest {

    public ProjectApplicationDaoTest() {
        super(10);
    }

    @Autowired
    private ProjectApplicationDao projectApplicationDao;

    @Test
    void countByProjectIds() {
        final Long[] projectIds = LongStream.rangeClosed(1, TEST_SIZE)
            .boxed()
            .toArray(Long[]::new);
        final Map<Long, Long> result = projectApplicationDao.countByProjectIds(projectIds);

        assertThat(result).hasSize(TEST_SIZE);
        assertThat(result.values()).allMatch(value -> value == TEST_SIZE);
    }
}