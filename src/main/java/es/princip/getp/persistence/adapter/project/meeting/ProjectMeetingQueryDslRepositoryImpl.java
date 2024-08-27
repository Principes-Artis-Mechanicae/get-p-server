package es.princip.getp.persistence.adapter.project.meeting;

import es.princip.getp.common.util.QueryDslSupport;
import es.princip.getp.persistence.adapter.client.QClientJpaEntity;
import es.princip.getp.persistence.adapter.project.commission.QProjectJpaEntity;

class ProjectMeetingQueryDslRepositoryImpl
    extends QueryDslSupport
    implements ProjectMeetingQueryDslRepository {

    private static final QProjectJpaEntity project = QProjectJpaEntity.projectJpaEntity;
    private static final QClientJpaEntity client = QClientJpaEntity.clientJpaEntity;

    @Override
    public boolean existsApplicantByProjectIdAndMemberId(final Long projectId, final Long memberId) {
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
