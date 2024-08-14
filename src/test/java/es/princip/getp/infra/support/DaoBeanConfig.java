package es.princip.getp.infra.support;

import es.princip.getp.domain.client.query.dao.ClientDao;
import es.princip.getp.domain.client.query.dao.ClientQueryDslDao;
import es.princip.getp.domain.like.query.dao.PeopleLikeDao;
import es.princip.getp.domain.like.query.dao.PeopleLikeQueryDslDao;
import es.princip.getp.domain.like.query.dao.ProjectLikeDao;
import es.princip.getp.domain.like.query.dao.ProjectLikeQueryDslDao;
import es.princip.getp.domain.people.query.dao.PeopleDao;
import es.princip.getp.domain.people.query.dao.PeopleQueryDslDao;
import es.princip.getp.domain.project.query.dao.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DaoBeanConfig {

    @Bean
    public ClientDao clientDao() {
        return new ClientQueryDslDao();
    }

    @Bean
    public ProjectLikeDao projectLikeDao() {
        return new ProjectLikeQueryDslDao();
    }

    @Bean
    public PeopleLikeDao peopleLikeDao() {
        return new PeopleLikeQueryDslDao();
    }

    @Bean
    public PeopleDao peopleDao() {
        return new PeopleQueryDslDao(peopleLikeDao());
    }

    @Bean
    public ProjectApplicationDao projectApplicationDao() {
        return new ProjectApplicationQueryDslDao();
    }

    @Bean
    public ProjectDao projectDao() {
        return new ProjectQueryDslDao(projectLikeDao(), projectApplicationDao());
    }

    @Bean
    public MyCommissionedProjectDao myCommissionedProjectDao() {
        return new MyCommissionedProjectQueryDslDao(projectApplicationDao());
    }
}
