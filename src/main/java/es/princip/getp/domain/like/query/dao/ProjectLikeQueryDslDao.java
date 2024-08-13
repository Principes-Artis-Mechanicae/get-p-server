package es.princip.getp.domain.like.query.dao;

import es.princip.getp.infra.support.QueryDslSupport;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static es.princip.getp.domain.like.command.domain.project.QProjectLike.projectLike;
import static java.util.stream.Collectors.toMap;

@Repository
public class ProjectLikeQueryDslDao extends QueryDslSupport implements ProjectLikeDao {

    @Override
    // TODO: 좋아요 수 조회 성능 개선 필요
    public Long countByLikedId(final Long projectId) {
        return queryFactory.select(projectLike.count())
            .from(projectLike)
            .where(projectLike.likedId.eq(projectId))
            .fetchOne();
    }

    @Override
    public Map<Long, Long> countByLikedIds(final Long... likedIds) {
        final Map<Long, Long> counts = Arrays.stream(likedIds)
            .collect(toMap(id -> id, id -> 0L));
        final Map<Long, Long> result = queryFactory.select(projectLike.likedId, projectLike.count())
            .from(projectLike)
            .where(projectLike.likedId.in(likedIds))
            .groupBy(projectLike.likedId)
            .fetch()
            .stream()
            .collect(
                toMap(
                    tuple -> tuple.get(projectLike.likedId),
                    tuple -> Optional.ofNullable(tuple.get(projectLike.count()))
                        .orElse(0L)
                )
            );
        counts.putAll(result);
        return counts;
    }
}
