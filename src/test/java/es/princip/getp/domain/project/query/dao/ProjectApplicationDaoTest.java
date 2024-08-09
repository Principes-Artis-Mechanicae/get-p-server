package es.princip.getp.domain.project.query.dao;

import es.princip.getp.domain.project.query.dto.AppliedProjectCardResponse;
import es.princip.getp.domain.project.query.infra.ProjectApplicationDaoConfig;
import es.princip.getp.infra.support.DaoTest;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    @Nested
    class 지원한_프로젝트_목록_조회 {
        final int pageSize = 10;
        final Pageable pageable = PageRequest.of(0, pageSize);

        @Test
        void 만료된_프로젝트도_보는_경우() {
            final Page<AppliedProjectCardResponse> response = projectApplicationDao.findPagedMyAppliedProjects(
                pageable,
                1L,
                true
            );

            assertThat(response.getContent()).allSatisfy(content -> {
                assertThat(content).usingRecursiveComparison().isNotNull();
            });
            assertThat(response.getNumberOfElements()).isGreaterThan(0);
        }
    }
}