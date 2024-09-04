package es.princip.getp.persistence.adapter.like.query;

import es.princip.getp.application.like.port.out.people.CountPeopleLikePort;
import es.princip.getp.common.util.QueryDslSupport;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static es.princip.getp.persistence.adapter.like.command.people.QPeopleLikeJpaEntity.peopleLikeJpaEntity;
import static java.util.stream.Collectors.toMap;

@Repository
public class CountPeopleLikeAdapter extends QueryDslSupport implements CountPeopleLikePort {

    @Override
    public Long countByPeopleId(Long peopleId) {
        return queryFactory.select(peopleLikeJpaEntity.count())
                .from(peopleLikeJpaEntity)
                .where(peopleLikeJpaEntity.peopleId.eq(peopleId))
                .fetchOne();
    }

    @Override
    public Map<Long, Long> countByPeopleIds(Long... peopleIds) {
        final Map<Long, Long> counts = Arrays.stream(peopleIds)
            .collect(toMap(id -> id, id -> 0L));
        final Map<Long, Long> result = queryFactory.select(peopleLikeJpaEntity.peopleId, peopleLikeJpaEntity.count())
            .from(peopleLikeJpaEntity)
            .where(peopleLikeJpaEntity.peopleId.in(peopleIds))
            .groupBy(peopleLikeJpaEntity.peopleId)
            .fetch()
            .stream()
            .collect(
                toMap(
                    tuple -> tuple.get(peopleLikeJpaEntity.peopleId),
                    tuple -> Optional.ofNullable(tuple.get(peopleLikeJpaEntity.count()))
                        .orElse(0L)
                )
            );
        counts.putAll(result);
        return counts;
    }
}
