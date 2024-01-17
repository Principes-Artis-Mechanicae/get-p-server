package es.princip.getp.domain.people.repository;

import es.princip.getp.global.config.QueryDslTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(QueryDslTestConfig.class)
class PeopleQueryDslRepositoryTest {
    @Autowired
    private PeopleQueryDslRepository peopleQueryDslRepository;

    // TODO: 테스트 코드 작성하기
}