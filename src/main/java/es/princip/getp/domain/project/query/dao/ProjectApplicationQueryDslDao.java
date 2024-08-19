package es.princip.getp.domain.project.query.dao;

import es.princip.getp.common.util.QueryDslSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static es.princip.getp.domain.project.command.domain.QProjectApplication.projectApplication;

@Repository
@RequiredArgsConstructor
public class ProjectApplicationQueryDslDao extends QueryDslSupport implements ProjectApplicationDao {

    @Override
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
