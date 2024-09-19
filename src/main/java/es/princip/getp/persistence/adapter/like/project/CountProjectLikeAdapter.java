package es.princip.getp.persistence.adapter.like.project;

import es.princip.getp.application.like.project.port.out.CountProjectLikePort;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.persistence.support.QueryDslSupport;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@Repository
class CountProjectLikeAdapter extends QueryDslSupport implements CountProjectLikePort {

    private static final QProjectLikeJpaEntity projectLike = QProjectLikeJpaEntity.projectLikeJpaEntity;

    @Override
    // TODO: 좋아요 수 조회 성능 개선 필요
    public Long countBy(final ProjectId projectId) {
        return queryFactory.select(projectLike.count())
            .from(projectLike)
            .where(projectLike.projectId.eq(projectId.getValue()))
            .fetchOne();
    }

    @Override
    public Map<ProjectId, Long> countBy(final ProjectId... projectIds) {
        final Map<ProjectId, Long> counts = Arrays.stream(projectIds)
            .collect(toMap(id -> id, id -> 0L));
        final Map<ProjectId, Long> result = queryFactory.select(projectLike.projectId, projectLike.count())
            .from(projectLike)
            .where(projectLike.projectId.in(
                Arrays.stream(projectIds)
                    .map(ProjectId::getValue)
                    .toArray(Long[]::new)
            ))
            .groupBy(projectLike.projectId)
            .fetch()
            .stream()
            .collect(
                toMap(
                    tuple -> new ProjectId(tuple.get(projectLike.projectId)),
                    tuple -> Optional.ofNullable(tuple.get(projectLike.count()))
                        .orElse(0L)
                )
            );
        counts.putAll(result);
        return counts;
    }
}
