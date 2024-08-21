package es.princip.getp.domain.project.command.infra;

import es.princip.getp.common.util.QueryDslSupport;
import es.princip.getp.persistence.adapter.client.QClientJpaEntity;

import static es.princip.getp.domain.project.command.domain.QProject.project;

public class ProjectMeetingQueryDslRepositoryImpl extends QueryDslSupport implements ProjectMeetingQueryDslRepository {

    private static final QClientJpaEntity client = QClientJpaEntity.clientJpaEntity;

    @Override
    public boolean existsByProjectIdAndMemberId(final Long projectId, final Long memberId) {
        final Integer count = queryFactory.selectOne()
            .from(project)
            .join(client)
            .on(
                project.clientId.eq(client.clientId)
                .and(project.projectId.eq(projectId))
                .and(client.memberId.eq(memberId))
            )
            .fetchFirst();
        return count != null;
    }
}
