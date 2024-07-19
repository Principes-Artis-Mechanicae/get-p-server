package es.princip.getp.domain.people.query.infra;

import es.princip.getp.domain.people.query.dao.PeopleDao;
import es.princip.getp.domain.people.query.dao.PeopleDaoImpl;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class PeopleDaoConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public PeopleDao peopleDao() {
        return new PeopleDaoImpl();
    }

    @Bean
    public DataLoader peopleDataLoader() {
        return new PeopleDataLoader(entityManager);
    }
}
