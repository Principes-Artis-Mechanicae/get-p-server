package es.princip.getp.domain.project.query.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import es.princip.getp.domain.project.query.dao.AppliedProjectDao;
import es.princip.getp.domain.project.query.dao.AppliedProjectQueryDslDao;
import es.princip.getp.domain.project.query.dao.ProjectApplicationDao;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@TestConfiguration
@Import(ProjectApplicationDaoConfig.class)
public class AppliedProjectDaoConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProjectApplicationDao projectApplicationDao;

    @Bean
    public AppliedProjectDao appliedProjectDao() {
        return new AppliedProjectQueryDslDao(projectApplicationDao);
    }

    @Bean
    public DataLoader myProjectDataLoader() {
        return new MyProjectDataLoader(entityManager);
    }
}