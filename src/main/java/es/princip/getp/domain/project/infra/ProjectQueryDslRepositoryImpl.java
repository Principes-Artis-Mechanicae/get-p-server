package es.princip.getp.domain.project.infra;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import es.princip.getp.domain.project.domain.Project;
import es.princip.getp.domain.project.domain.QProject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

@RequiredArgsConstructor
public class ProjectQueryDslRepositoryImpl implements ProjectQueryDslRepository {

    private final JPAQueryFactory queryFactory;
    private final QProject project = QProject.project;

    public Page<Project> findProjectPage(Pageable pageable) {
        List<Project> content = getProjectContent(pageable);
        JPAQuery<Long> countQuery = getProjectCountQuery();
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private List<Project> getProjectContent(Pageable pageable) {
        return queryFactory.selectFrom(project)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private JPAQuery<Long> getProjectCountQuery() {
        return queryFactory.select(project.count()).from(project);
    }
}
