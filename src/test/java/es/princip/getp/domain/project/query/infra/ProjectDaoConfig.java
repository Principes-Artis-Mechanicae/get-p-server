package es.princip.getp.domain.project.query.infra;

import es.princip.getp.domain.project.query.dao.ProjectApplicationDao;
import es.princip.getp.domain.project.query.dao.ProjectDao;
import es.princip.getp.domain.project.query.dao.ProjectLikeDao;
import es.princip.getp.domain.project.query.dao.ProjectQueryDslDao;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({ProjectApplicationDaoConfig.class, ProjectLikeDaoConfig.class})
public class ProjectDaoConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProjectLikeDao projectLikeDao;

    @Autowired
    private ProjectApplicationDao projectApplicationDao;

    @Bean
    public ProjectDao projectDao() {
        return new ProjectQueryDslDao(projectLikeDao, projectApplicationDao);
    }

    @Bean
    public DataLoader projectDataLoader() {
        return new ProjectDataLoader(entityManager);
    }
}
