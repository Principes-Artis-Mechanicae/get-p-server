package es.princip.getp.domain.project.query.infra;

import es.princip.getp.domain.project.query.dao.ProjectDao;
import es.princip.getp.domain.project.query.dao.ProjectDaoImpl;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ProjectDaoConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public ProjectDao projectDao() {
        return new ProjectDaoImpl();
    }

    @Bean
    public DataLoader projectDataLoader() {
        return new ProjectDataLoader(entityManager);
    }
}
