package es.princip.getp.persistence.adapter.project.apply;

import com.querydsl.core.types.dsl.BooleanExpression;
import es.princip.getp.application.project.apply.port.out.CountProjectApplicationPort;
import es.princip.getp.domain.project.apply.model.ProjectApplicationStatus;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.persistence.adapter.project.apply.model.QProjectApplicationJpaEntity;
import es.princip.getp.persistence.adapter.project.apply.model.QTeammateJpaEntity;
import es.princip.getp.persistence.support.QueryDslSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static es.princip.getp.domain.project.apply.model.ProjectApplicationStatus.PENDING_TEAM_APPROVAL;

@Repository
@RequiredArgsConstructor
class CountProjectApplicationAdapter extends QueryDslSupport implements CountProjectApplicationPort {

    private static final QProjectApplicationJpaEntity application
        = QProjectApplicationJpaEntity.projectApplicationJpaEntity;
    private static final QTeammateJpaEntity teammate = QTeammateJpaEntity.teammateJpaEntity;

    private static final ProjectApplicationStatus[] statusesWithoutPendingTeamApproval
        = Arrays.stream(ProjectApplicationStatus.values())
            .filter(status -> !status.equals(PENDING_TEAM_APPROVAL))
            .toArray(ProjectApplicationStatus[]::new);

    private static BooleanExpression applicationStatusNePendingTeamApproval() {
        return application.status.in(statusesWithoutPendingTeamApproval);
    }

    private static BooleanExpression teammateApplicationStatusNePendingTeamApproval() {
        return teammate.application.status.in(statusesWithoutPendingTeamApproval);
    }

    @Override
    public Map<ProjectId, Long> countBy(final ProjectId... projectId) {
        final Long[] ids = Arrays.stream(projectId)
            .map(ProjectId::getValue)
            .toArray(Long[]::new);

        final Map<ProjectId, Long> counts = queryFactory.select(application.projectId, application.count())
            .from(application)
            .where(application.projectId.in(ids)
                .and(applicationStatusNePendingTeamApproval()))
            .groupBy(application.projectId)
            .fetch()
            .stream()
            .collect(Collectors.toMap(
                tuple -> new ProjectId(tuple.get(application.projectId)),
                tuple -> Optional.ofNullable(tuple.get(application.count()))
                    .orElse(0L)
            ));

        queryFactory.select(teammate.application.projectId, teammate.count())
            .from(teammate)
            .where(teammate.application.projectId.in(ids)
                .and(teammateApplicationStatusNePendingTeamApproval()))
            .groupBy(teammate.application.projectId)
            .fetch()
            .forEach(tuple -> {
                final ProjectId key = new ProjectId(tuple.get(teammate.application.projectId));
                final Long value = Optional.ofNullable(tuple.get(teammate.count()))
                    .orElse(0L);
                counts.put(key, counts.getOrDefault(key, 0L) + value);
            });

        return counts;
    }

    @Override
    public Long countBy(final ProjectId projectId) {
        Long count = queryFactory.select(application.count())
            .from(application)
            .where(application.projectId.eq(projectId.getValue())
                .and(applicationStatusNePendingTeamApproval()))
            .fetchOne();

        count += queryFactory.select(teammate.count())
            .from(teammate)
            .where(teammate.application.projectId.eq(projectId.getValue())
                .and(teammateApplicationStatusNePendingTeamApproval()))
            .fetchOne();

        return count;
    }
}