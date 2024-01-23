package es.princip.getp.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import es.princip.getp.domain.people.repository.PeopleQueryDslRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class QueryDslTestConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public PeopleQueryDslRepository peopleQueryDslRepository() {
        return new PeopleQueryDslRepository(queryFactory());
    }
}