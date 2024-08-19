package es.princip.getp.domain.project.query.infra;

import es.princip.getp.domain.project.query.dao.ProjectApplicationDao;
import es.princip.getp.domain.project.query.dao.ProjectApplicationQueryDslDao;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ProjectApplicationDaoConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public ProjectApplicationDao projectApplicationDao() {
        return new ProjectApplicationQueryDslDao();
    }

    @Bean
    public DataLoader projectApplicationDataLoader() {
        return new ProjectApplicationDataLoader(entityManager);
    }
}
