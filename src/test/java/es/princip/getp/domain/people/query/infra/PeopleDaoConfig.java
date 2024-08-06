package es.princip.getp.domain.people.query.infra;

import es.princip.getp.domain.like.query.dao.PeopleLikeDao;
import es.princip.getp.domain.people.query.dao.PeopleDao;
import es.princip.getp.domain.people.query.dao.PeopleQueryDslDao;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@TestConfiguration
public class PeopleDaoConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    protected PeopleLikeDao peopleLikeDao() {
        return new PeopleLikeDao() {
            @Override
            public Long countByLikedId(Long likedId) {
                return 0L;
            }

            @Override
            public Map<Long, Long> countByLikedIds(final Long... likedIds) {
                return Arrays.stream(likedIds)
                    .collect(Collectors.toMap(id -> id, id -> 0L));
            }
        };
    }

    @Bean
    public PeopleDao peopleDao() {
        return new PeopleQueryDslDao(peopleLikeDao());
    }

    @Bean
    public DataLoader peopleDataLoader() {
        return new PeopleDataLoader(entityManager);
    }
}
