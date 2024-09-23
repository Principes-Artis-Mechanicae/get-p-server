package es.princip.getp.persistence.adapter.like.project;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectSearchOrder;
import es.princip.getp.application.like.project.port.out.FindLikedProjectPort;
import es.princip.getp.application.project.apply.port.out.CountProjectApplicationPort;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.persistence.adapter.member.QMemberJpaEntity;
import es.princip.getp.persistence.adapter.project.ProjectPersistenceMapper;
import es.princip.getp.persistence.adapter.project.commission.ProjectJpaEntity;
import es.princip.getp.persistence.adapter.project.commission.QProjectJpaEntity;
import es.princip.getp.persistence.support.QueryDslSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static es.princip.getp.persistence.adapter.project.ProjectPersistenceUtil.toProjectIds;

@Repository
@RequiredArgsConstructor
class FindLikedProjectAdapter extends QueryDslSupport implements FindLikedProjectPort {

    private static final QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;
    private static final QProjectJpaEntity project = QProjectJpaEntity.projectJpaEntity;
    private static final QProjectLikeJpaEntity like = QProjectLikeJpaEntity.projectLikeJpaEntity;
    private final CountProjectApplicationPort countProjectApplicationPort;
    private final ProjectPersistenceMapper mapper;

    @Override
    public Page<ProjectCardResponse> findBy(
        final MemberId memberId,
        final Pageable pageable
    ) {
        final List<Project> projects = getContent(pageable, memberId);
        final ProjectId[] projectIds = toProjectIds(projects);
        final Map<ProjectId, Long> projectApplicationCounts = countProjectApplicationPort.countBy(projectIds);
        final List<ProjectCardResponse> content = assemble(projects, projectApplicationCounts);

        return applyPagination(
            pageable,
            content,
            countQuery -> getCountQuery(memberId)
        );
    }

    private <T> JPAQuery<T> buildQuery(final JPAQuery<T> selectQuery, final MemberId memberId) {
        return selectQuery.from(project)
            .join(like).on(project.id.eq(like.projectId))
            .join(member).on(like.memberId.eq(member.id))
            .where(member.id.eq(memberId.getValue()));
    }

    private JPAQuery<Long> getCountQuery(final MemberId memberId) {
        return buildQuery(queryFactory.select(project.count()), memberId);
    }

    private JPAQuery<ProjectJpaEntity> getContentQuery(final Pageable pageable, final MemberId memberId) {
        return buildQuery(queryFactory.select(project), memberId)
            .orderBy(orderSpecifiersFromSort(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());
    }

    private List<Project> getContent(final Pageable pageable, final MemberId memberId) {
        return getContentQuery(pageable, memberId)
            .fetch()
            .stream()
            .map(mapper::mapToDomain)
            .toList();
    }

    private List<ProjectCardResponse> assemble(
        final List<Project> projects,
        final Map<ProjectId, Long> projectApplicationCounts
    ) {
        return projects.stream()
            .map(project -> ProjectCardResponse.of(
                project,
                projectApplicationCounts.get(project.getId())
            ))
            .toList();
    }

    private static OrderSpecifier<?> toOrderSpecifier(final Sort.Order order, final ProjectSearchOrder searchOrder) {
        final Order converted = convertTo(order);
        return switch (searchOrder) {
            case CREATED_AT -> new OrderSpecifier<>(converted, project.createdAt);
            case PAYMENT -> new OrderSpecifier<>(converted, project.payment);
            case APPLICATION_DURATION -> new OrderSpecifier<>(converted, project.applicationDuration.endDate);
            case PROJECT_ID -> new OrderSpecifier<>(converted, project.id);
        };
    }

    static OrderSpecifier<?>[] orderSpecifiersFromSort(Sort sort) {
        return sort.stream()
            .map(order -> toOrderSpecifier(order, ProjectSearchOrder.get(order.getProperty())))
            .toArray(OrderSpecifier[]::new);
    }
}
