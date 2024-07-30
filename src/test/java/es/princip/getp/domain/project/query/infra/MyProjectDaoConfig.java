package es.princip.getp.domain.project.query.infra;

import es.princip.getp.domain.project.query.dao.MyProjectDao;
import es.princip.getp.domain.project.query.dao.MyProjectQueryDslDao;
import es.princip.getp.domain.project.query.dao.ProjectApplicationDao;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(ProjectApplicationDaoConfig.class)
public class MyProjectDaoConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProjectApplicationDao projectApplicationDao;

    @Bean
    public MyProjectDao myProjectDao() {
        return new MyProjectQueryDslDao(projectApplicationDao);
    }

    @Bean
    public DataLoader myProjectDataLoader() {
        return new MyProjectDataLoader(entityManager);
    }
}
