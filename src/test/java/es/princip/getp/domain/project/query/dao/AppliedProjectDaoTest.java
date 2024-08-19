package es.princip.getp.domain.project.query.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import es.princip.getp.domain.project.query.dto.AppliedProjectCardResponse;
import es.princip.getp.domain.project.query.infra.AppliedProjectDaoConfig;
import es.princip.getp.infra.support.DaoTest;

@Import(AppliedProjectDaoConfig.class)
public class AppliedProjectDaoTest extends DaoTest{

    public AppliedProjectDaoTest() {
        super(10);
    }

    @Autowired
    private AppliedProjectDao appliedProjectDao;

    @Nested
    class 지원한_프로젝트_목록_조회 {
        final int pageSize = 10;
        final Pageable pageable = PageRequest.of(0, pageSize);

        @Test
        void 프로젝트_목록_조회() {
            final Page<AppliedProjectCardResponse> response = appliedProjectDao.findPagedMyAppliedProjects(
                pageable,
                1L
            );

            assertThat(response.getContent()).allSatisfy(content -> {
                assertThat(content).usingRecursiveComparison().isNotNull();
            });
            assertThat(response.getNumberOfElements()).isGreaterThan(0);
        }
    }
}
