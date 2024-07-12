package es.princip.getp.domain.project.infra;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import es.princip.getp.domain.project.domain.QProject;
import es.princip.getp.domain.project.dto.response.CardProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static es.princip.getp.domain.client.domain.QClient.client;
import static es.princip.getp.domain.member.domain.model.QMember.member;

@RequiredArgsConstructor
public class ProjectQueryDslRepositoryImpl implements ProjectQueryDslRepository {

    private final JPAQueryFactory queryFactory;
    private final QProject project = QProject.project;

    public Page<CardProjectResponse> findToCardProjectResponse(Pageable pageable) {
        List<CardProjectResponse> response = getProjectContent(pageable).stream()
            .map(tuple -> CardProjectResponse.from(tuple.get(project), tuple.get(client), tuple.get(member)))
            .toList();
        JPAQuery<Long> countQuery = getProjectCountQuery();
        return PageableExecutionUtils.getPage(response, pageable, countQuery::fetchOne);
    }

    private List<Tuple> getProjectContent(Pageable pageable) {
        return queryFactory.select(project, client, member)
            .from(project)
            .join(client).on(project.clientId.eq(client.clientId))
            .join(member).on(client.memberId.eq(member.memberId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private JPAQuery<Long> getProjectCountQuery() {
        return queryFactory.select(project.count()).from(project);
    }
}
