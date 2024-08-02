package es.princip.getp.domain.project.command.domain;

import static es.princip.getp.domain.client.command.domain.QClient.client;
import static es.princip.getp.domain.project.command.domain.QProject.project;

import es.princip.getp.infra.support.QueryDslSupport;

public class ProjectMeetingQueryDslRepositoryImpl extends QueryDslSupport implements ProjectMeetingQueryDslRepository{
    @Override
    public boolean existsByProjectIdAndMemberId(Long projectId, Long memberId) {
        Long count = queryFactory.select(project.projectId.count())
            .from(project)
            .join(client)
            .on(
                project.clientId.eq(client.clientId)
                .and(project.projectId.eq(projectId))
                .and(client.memberId.eq(memberId))
            )
            .fetchOne();
        return count != null && count > 0;
    }
}
