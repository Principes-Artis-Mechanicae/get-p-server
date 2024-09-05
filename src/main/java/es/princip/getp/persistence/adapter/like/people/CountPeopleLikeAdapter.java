package es.princip.getp.persistence.adapter.like.people;

import es.princip.getp.application.like.people.port.out.CountPeopleLikePort;
import es.princip.getp.persistence.support.QueryDslSupport;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@Repository
public class CountPeopleLikeAdapter extends QueryDslSupport implements CountPeopleLikePort {

    private static final QPeopleLikeJpaEntity peopleLike = QPeopleLikeJpaEntity.peopleLikeJpaEntity;

    @Override
    public Long countBy(Long peopleId) {
        return queryFactory.select(peopleLike.count())
                .from(peopleLike)
                .where(peopleLike.peopleId.eq(peopleId))
                .fetchOne();
    }

    @Override
    public Map<Long, Long> countBy(Long... peopleIds) {
        final Map<Long, Long> counts = Arrays.stream(peopleIds)
            .collect(toMap(id -> id, id -> 0L));
        final Map<Long, Long> result = queryFactory.select(peopleLike.peopleId, peopleLike.count())
            .from(peopleLike)
            .where(peopleLike.peopleId.in(peopleIds))
            .groupBy(peopleLike.peopleId)
            .fetch()
            .stream()
            .collect(
                toMap(
                    tuple -> tuple.get(peopleLike.peopleId),
                    tuple -> Optional.ofNullable(tuple.get(peopleLike.count()))
                        .orElse(0L)
                )
            );
        counts.putAll(result);
        return counts;
    }
}
