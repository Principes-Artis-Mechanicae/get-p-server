package es.princip.getp.domain.like.query.infra;

import es.princip.getp.domain.like.query.dao.PeopleLikeDao;
import es.princip.getp.domain.like.query.dao.PeopleLikeQueryDslDao;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class PeopleLikeDaoConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public PeopleLikeDao peopleLikeDao() {
        return new PeopleLikeQueryDslDao();
    }

    @Bean
    public DataLoader peopleLikeDataLoader() {
        return new PeopleLikeDataLoader(entityManager);
    }
}
