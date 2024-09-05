package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.persistence.support.QueryDslSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FindProjectApplicationAdapter extends QueryDslSupport {

    private static final QProjectApplicationJpaEntity projectApplication
        = QProjectApplicationJpaEntity.projectApplicationJpaEntity;

    public Map<Long, Long> countByProjectIds(final Long... projectId) {
        return queryFactory.select(projectApplication.projectId, projectApplication.count())
            .from(projectApplication)
            .where(projectApplication.projectId.in(projectId))
            .groupBy(projectApplication.projectId)
            .fetch()
            .stream()
            .collect(Collectors.toMap(
                tuple -> tuple.get(projectApplication.projectId),
                tuple -> Optional.ofNullable(tuple.get(projectApplication.count()))
                    .orElse(0L)
            ));
    }
}