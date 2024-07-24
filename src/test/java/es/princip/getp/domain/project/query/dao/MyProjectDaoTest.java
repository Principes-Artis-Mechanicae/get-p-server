package es.princip.getp.domain.project.query.dao;

import es.princip.getp.domain.project.command.domain.ProjectStatus;
import es.princip.getp.domain.project.query.dto.MyProjectCardResponse;
import es.princip.getp.domain.project.query.infra.MyProjectDaoConfig;
import es.princip.getp.infra.support.DaoTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@Import(MyProjectDaoConfig.class)
@Slf4j
class MyProjectDaoTest extends DaoTest {

    public MyProjectDaoTest() {
        super(10);
    }

    @Autowired
    private MyProjectDao myProjectDao;

    @Nested
    class 내_프로젝트_목록_페이지_조회 {

        final int pageSize = 10;
        final Pageable pageable = PageRequest.of(0, pageSize);

        @Test
        void 만료된_프로젝트도_보는_경우() {
            final Page<MyProjectCardResponse> response = myProjectDao.findPagedMyProjectCard(
                pageable,
                1L,
                true
            );

            assertThat(response.getContent()).allSatisfy(content -> {
                assertThat(content).usingRecursiveComparison().isNotNull();
            });
            assertThat(response.getNumberOfElements()).isGreaterThan(0);
        }

        @Test
        void 만료된_프로젝트는_보지_않는_경우() {
            final Page<MyProjectCardResponse> response = myProjectDao.findPagedMyProjectCard(
                pageable,
                1L,
                false
            );

            assertThat(response.getContent()).allSatisfy(content -> {
                assertThat(content).usingRecursiveComparison().isNotNull();
                assertThat(content.status()).isNotEqualTo(ProjectStatus.CANCELLED);
            });
            assertThat(response.getNumberOfElements()).isGreaterThan(0);
        }
    }
}