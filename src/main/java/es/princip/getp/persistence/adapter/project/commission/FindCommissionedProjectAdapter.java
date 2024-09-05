package es.princip.getp.persistence.adapter.project.commission;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import es.princip.getp.api.controller.project.query.dto.CommissionedProjectCardResponse;
import es.princip.getp.api.controller.project.query.dto.MyProjectSearchOrder;
import es.princip.getp.application.project.commission.port.out.FindCommissionedProjectPort;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import es.princip.getp.persistence.adapter.client.QClientJpaEntity;
import es.princip.getp.persistence.adapter.project.ProjectPersistenceMapper;
import es.princip.getp.persistence.adapter.project.apply.FindProjectApplicationAdapter;
import es.princip.getp.persistence.support.QueryDslSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
import static es.princip.getp.persistence.adapter.project.ProjectPersistenceUtil.toProjectIds;

@Repository
@RequiredArgsConstructor
class FindCommissionedProjectAdapter extends QueryDslSupport implements FindCommissionedProjectPort {

    private static final QProjectJpaEntity project = QProjectJpaEntity.projectJpaEntity;
    private static final QClientJpaEntity client = QClientJpaEntity.clientJpaEntity;
    private final FindProjectApplicationAdapter projectApplicationQuery;
    private final ProjectPersistenceMapper mapper;

    @Override
    public Page<CommissionedProjectCardResponse> findBy(
        final Long memberId,
        final Boolean cancelled,
        final Pageable pageable
    ) {
        final List<Project> projects = getMyProjects(pageable, memberId, cancelled);
        final Long[] projectIds = toProjectIds(projects);
        final Map<Long, Long> projectApplicationCounts = projectApplicationQuery.countByProjectIds(projectIds);
        final List<CommissionedProjectCardResponse> content = assembleProjectCardResponse(projects, projectApplicationCounts);

        return applyPagination(
            pageable,
            content,
            countQuery -> getMyProjectsCountQuery(memberId, cancelled)
        );
    }

    private List<CommissionedProjectCardResponse> assembleProjectCardResponse(
        final List<Project> projects,
        final Map<Long, Long> projectApplicationCounts
    ) {
        return projects.stream()
            .map(project -> CommissionedProjectCardResponse.of(
                project,
                projectApplicationCounts.get(project.getProjectId())
            ))
            .toList();
    }

    private JPAQuery<Long> getMyProjectsCountQuery(final Long memberId, final Boolean cancelled) {
        return queryFactory.select(project.count())
            .from(project)
            .join(client).on(project.clientId.eq(client.clientId))
            .where(client.memberId.eq(memberId), cancelledFilter(cancelled));
    }

    private List<Project> getMyProjects(final Pageable pageable, final Long memberId, final Boolean cancelled) {
        return queryFactory.selectFrom(project)
            .join(client).on(project.clientId.eq(client.clientId))
            .where(client.memberId.eq(memberId), cancelledFilter(cancelled))
            .orderBy(orderSpecifiersFromSort(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(mapper::mapToDomain)
            .toList();
    }

    private BooleanExpression cancelledFilter(final Boolean cancelled) {
        return cancelled ? null : project.status.ne(ProjectStatus.CANCELLED);
    }

    private static Order convertTo(final Sort.Order order) {
        return order.isAscending() ? ASC : DESC;
    }

    private static OrderSpecifier<?> toOrderSpecifier(final Sort.Order order, final MyProjectSearchOrder searchOrder) {
        final Order converted = convertTo(order);
        return switch (searchOrder) {
            case CREATED_AT -> new OrderSpecifier<>(converted, project.createdAt);
            case PAYMENT -> new OrderSpecifier<>(converted, project.payment);
            case APPLICATION_DURATION -> new OrderSpecifier<>(converted, project.applicationDuration.endDate);
            case PROJECT_ID -> new OrderSpecifier<>(converted, project.projectId);
        };
    }

    static OrderSpecifier<?>[] orderSpecifiersFromSort(Sort sort) {
        return sort.stream()
            .map(order -> toOrderSpecifier(order, MyProjectSearchOrder.get(order.getProperty())))
            .toArray(OrderSpecifier[]::new);
    }
}
