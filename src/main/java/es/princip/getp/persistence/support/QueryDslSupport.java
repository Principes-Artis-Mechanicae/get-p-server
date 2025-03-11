package es.princip.getp.persistence.support;

import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Function;

/**
 * QueryDsl 5.x 버전에 맞춘 QueryDsl 지원 라이브러리
 *
 * @author Jihun Yu
 * @see org.springframework.data.jpa.repository.support.QuerydslRepositorySupport 기반으로 한 Younghan Kim의 QueryDsl 4.x 지원 라이브러리
 *
 */
@Repository
public abstract class QueryDslSupport {

    private EntityManager entityManager;
    protected JPAQueryFactory queryFactory;

    @Autowired
    public void setEntityManager(final EntityManager entityManager) {
        Assert.notNull(entityManager, "EntityManager must not be null!");
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
    }

    @PostConstruct
    public void validate() {
        Assert.notNull(entityManager, "EntityManager must not be null!");
        Assert.notNull(queryFactory, "QueryFactory must not be null!");
    }

    protected <T> Page<T> applyPagination(
        final Pageable pageable,
        final List<T> content,
        final Function<JPAQueryFactory, JPAQuery<Long>> countQuery
    ) {
        JPAQuery<Long> countResult = countQuery.apply(queryFactory);
        return PageableExecutionUtils.getPage(content, pageable, countResult::fetchOne);
    }
}