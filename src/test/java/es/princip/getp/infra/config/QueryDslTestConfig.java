package es.princip.getp.infra.config;

import es.princip.getp.domain.people.query.dao.PeopleDao;
import es.princip.getp.domain.people.query.dao.PeopleDaoImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class QueryDslTestConfig {

    @Bean
    public PeopleDao peopleDao() {
        return new PeopleDaoImpl();
    }
}