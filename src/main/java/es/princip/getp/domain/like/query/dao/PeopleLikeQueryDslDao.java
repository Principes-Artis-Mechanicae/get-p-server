package es.princip.getp.domain.like.query.dao;

import es.princip.getp.common.util.QueryDslSupport;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static es.princip.getp.domain.like.command.domain.people.QPeopleLike.peopleLike;
import static java.util.stream.Collectors.toMap;

@Repository
public class PeopleLikeQueryDslDao extends QueryDslSupport implements PeopleLikeDao {

    @Override
    public Long countByLikedId(Long likedId) {
        return queryFactory.select(peopleLike.count())
                .from(peopleLike)
                .where(peopleLike.likedId.eq(likedId))
                .fetchOne();
    }

    @Override
    public Map<Long, Long> countByLikedIds(Long... likedIds) {
        final Map<Long, Long> counts = Arrays.stream(likedIds)
            .collect(toMap(id -> id, id -> 0L));
        final Map<Long, Long> result = queryFactory.select(peopleLike.likedId, peopleLike.count())
            .from(peopleLike)
            .where(peopleLike.likedId.in(likedIds))
            .groupBy(peopleLike.likedId)
            .fetch()
            .stream()
            .collect(
                toMap(
                    tuple -> tuple.get(peopleLike.likedId),
                    tuple -> Optional.ofNullable(tuple.get(peopleLike.count()))
                        .orElse(0L)
                )
            );
        counts.putAll(result);
        return counts;
    }
}
