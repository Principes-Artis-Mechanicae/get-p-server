package es.princip.getp.domain.project.command.infra;

import es.princip.getp.infra.support.QueryDslSupport;

import static es.princip.getp.domain.client.command.domain.QClient.client;
import static es.princip.getp.domain.project.command.domain.QProject.project;

public class ProjectMeetingQueryDslRepositoryImpl extends QueryDslSupport implements ProjectMeetingQueryDslRepository {

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
