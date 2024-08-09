package es.princip.getp.domain.project.query.infra;

import es.princip.getp.domain.project.query.dao.ProjectApplicationDao;
import es.princip.getp.domain.project.query.dao.ProjectApplicationQueryDslDao;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ProjectApplicationDaoConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProjectApplicationDao projectApplicationDao;

    @Bean
    public ProjectApplicationDao projectApplicationDao() {
        return new ProjectApplicationQueryDslDao(projectApplicationDao);
    }

    @Bean
    public DataLoader projectApplicationDataLoader() {
        return new ProjectApplicationDataLoader(entityManager);
    }
}
