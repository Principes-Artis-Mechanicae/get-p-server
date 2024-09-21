package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.application.project.apply.port.out.CountProjectApplicationPort;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.persistence.support.QueryDslSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
class CountProjectApplicationAdapter extends QueryDslSupport implements CountProjectApplicationPort {

    private static final QProjectApplicationJpaEntity projectApplication
        = QProjectApplicationJpaEntity.projectApplicationJpaEntity;

    public Map<ProjectId, Long> countBy(final ProjectId... projectId) {
        final Long[] ids = Arrays.stream(projectId)
            .map(ProjectId::getValue)
            .toArray(Long[]::new);
        return queryFactory.select(projectApplication.projectId, projectApplication.count())
            .from(projectApplication)
            .where(projectApplication.projectId.in(ids))
            .groupBy(projectApplication.projectId)
            .fetch()
            .stream()
            .collect(Collectors.toMap(
                tuple -> new ProjectId(tuple.get(projectApplication.projectId)),
                tuple -> Optional.ofNullable(tuple.get(projectApplication.count()))
                    .orElse(0L)
            ));
    }

    @Override
    public Long countBy(final ProjectId projectId) {
        return queryFactory.select(projectApplication.count())
            .from(projectApplication)
            .where(projectApplication.projectId.eq(projectId.getValue()))
            .fetchOne();
    }
}