package es.princip.getp.persistence.adapter.like.query;

import es.princip.getp.application.like.port.out.project.CountProjectLikePort;
import es.princip.getp.common.util.QueryDslSupport;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static es.princip.getp.persistence.adapter.like.command.project.QProjectLikeJpaEntity.projectLikeJpaEntity;
import static java.util.stream.Collectors.toMap;

@Repository
public class CountProjectLikeAdapter extends QueryDslSupport implements CountProjectLikePort {

    @Override
    // TODO: 좋아요 수 조회 성능 개선 필요
    public Long countByprojectId(final Long projectId) {
        return queryFactory.select(projectLikeJpaEntity.count())
            .from(projectLikeJpaEntity)
            .where(projectLikeJpaEntity.projectId.eq(projectId))
            .fetchOne();
    }

    @Override
    public Map<Long, Long> countByprojectIds(final Long... projectIds) {
        final Map<Long, Long> counts = Arrays.stream(projectIds)
            .collect(toMap(id -> id, id -> 0L));
        final Map<Long, Long> result = queryFactory.select(projectLikeJpaEntity.projectId, projectLikeJpaEntity.count())
            .from(projectLikeJpaEntity)
            .where(projectLikeJpaEntity.projectId.in(projectIds))
            .groupBy(projectLikeJpaEntity.projectId)
            .fetch()
            .stream()
            .collect(
                toMap(
                    tuple -> tuple.get(projectLikeJpaEntity.projectId),
                    tuple -> Optional.ofNullable(tuple.get(projectLikeJpaEntity.count()))
                        .orElse(0L)
                )
            );
        counts.putAll(result);
        return counts;
    }
}
