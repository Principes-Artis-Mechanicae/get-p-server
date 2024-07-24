package es.princip.getp.domain.project.query.dao;

import es.princip.getp.domain.project.query.dto.MyProjectCardResponse;
import es.princip.getp.domain.project.query.infra.MyProjectDaoConfig;
import es.princip.getp.infra.support.DaoTest;
import lombok.extern.slf4j.Slf4j;
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

    @Test
    void findPagedMyProjectCard() {
        final int pageSize = 10;
        final Pageable pageable = PageRequest.of(0, pageSize);
        final Page<MyProjectCardResponse> response = myProjectDao.findPagedMyProjectCard(pageable, 1L);

        assertThat(response.getContent()).allSatisfy(content -> {
            assertThat(content).usingRecursiveComparison().isNotNull();
        });
        assertThat(response.getTotalElements()).isEqualTo(TEST_SIZE);
        assertThat(response.getNumberOfElements()).isEqualTo(pageSize);
    }
}