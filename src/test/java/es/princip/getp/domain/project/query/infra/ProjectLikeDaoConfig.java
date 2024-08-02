package es.princip.getp.domain.project.query.infra;

import es.princip.getp.domain.project.query.dao.ProjectLikeDao;
import es.princip.getp.domain.project.query.dao.ProjectLikeQueryDslDao;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ProjectLikeDaoConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public ProjectLikeDao projectLikeDao() {
        return new ProjectLikeQueryDslDao();
    }

    @Bean
    public DataLoader projectLikeDataLoader() {
        return new ProjectLikeDataLoader(entityManager);
    }
}
