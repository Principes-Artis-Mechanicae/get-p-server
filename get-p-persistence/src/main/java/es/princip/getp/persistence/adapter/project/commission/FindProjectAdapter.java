package es.princip.getp.persistence.adapter.project.commission;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import es.princip.getp.application.client.port.out.ClientQuery;
import es.princip.getp.application.like.project.port.out.CheckProjectLikePort;
import es.princip.getp.application.like.project.port.out.CountProjectLikePort;
import es.princip.getp.application.project.apply.port.out.CountProjectApplicationPort;
import es.princip.getp.application.project.commission.dto.command.ProjectSearchFilter;
import es.princip.getp.application.project.commission.dto.command.ProjectSearchOrder;
import es.princip.getp.application.project.commission.dto.response.ProjectCardResponse;
import es.princip.getp.application.project.commission.dto.response.ProjectClientResponse;
import es.princip.getp.application.project.commission.dto.response.ProjectDetailResponse;
import es.princip.getp.application.project.commission.exception.NotFoundProjectException;
import es.princip.getp.application.project.commission.port.out.FindProjectPort;
import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import es.princip.getp.persistence.adapter.client.QClientJpaEntity;
import es.princip.getp.persistence.adapter.like.project.QProjectLikeJpaEntity;
import es.princip.getp.persistence.adapter.member.QMemberJpaEntity;
import es.princip.getp.persistence.adapter.people.model.QPeopleJpaEntity;
import es.princip.getp.persistence.adapter.project.ProjectPersistenceMapper;
import es.princip.getp.persistence.adapter.project.ProjectQueryMapper;
import es.princip.getp.persistence.adapter.project.apply.model.QProjectApplicationJpaEntity;
import es.princip.getp.persistence.adapter.project.commission.model.ProjectJpaEntity;
import es.princip.getp.persistence.adapter.project.commission.model.QProjectJpaEntity;
import es.princip.getp.persistence.support.QueryDslSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static es.princip.getp.persistence.adapter.project.ProjectPersistenceUtil.toProjectIds;

@Repository
@RequiredArgsConstructor
class FindProjectAdapter extends QueryDslSupport implements FindProjectPort {

    private static final QProjectApplicationJpaEntity application = QProjectApplicationJpaEntity.projectApplicationJpaEntity;
    private static final QProjectLikeJpaEntity like = QProjectLikeJpaEntity.projectLikeJpaEntity;
    private static final QClientJpaEntity client = QClientJpaEntity.clientJpaEntity;
    private static final QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;
    private static final QPeopleJpaEntity people = QPeopleJpaEntity.peopleJpaEntity;
    private static final QProjectJpaEntity project = QProjectJpaEntity.projectJpaEntity;

    private final ClientQuery clientQuery;
    private final CountProjectLikePort countProjectLikePort;
    private final CountProjectApplicationPort countProjectApplicationPort;
    private final CheckProjectLikePort checkProjectLikePort;
    private final ProjectPersistenceMapper persistenceMapper;
    private final ProjectQueryMapper queryMapper;

    private BooleanExpression memberIdEq(final MemberId memberId) {
        return Optional.ofNullable(memberId)
            .map(MemberId::getValue)
            .map(member.id::eq)
            .orElse(null);
    }

    private <T> JPAQuery<T> buildQuery(
        final JPAQuery<T> selectFrom,
        final ProjectSearchFilter filter,
        final MemberId memberId
    ) {
        if (filter.isApplied()) {
            selectFrom.join(application).on(project.id.eq(application.projectId))
                .join(people).on(application.applicantId.eq(people.id))
                .join(member).on(people.memberId.eq(member.id))
                .where(memberIdEq(memberId));
        }
        if (filter.isLiked()) {
            selectFrom.join(like).on(project.id.eq(like.projectId))
                .join(member).on(like.memberId.eq(member.id))
                .where(memberIdEq(memberId));
        }
        if (filter.isCommissioned()) {
            selectFrom.join(client).on(project.clientId.eq(client.id))
                .join(member).on(client.memberId.eq(member.id))
                .where(memberIdEq(memberId));
        }
        if (filter.isClosed()) {
            selectFrom.where(project.status.eq(ProjectStatus.CANCELLED));
        }
        return selectFrom;
    }

    private List<ProjectCardResponse> assemble(
        final List<Project> projects,
        final Map<ProjectId, Long> projectApplicationCounts
    ) {
        return projects.stream()
            .map(project -> ProjectCardResponse.of(
                project,
                projectApplicationCounts.getOrDefault(project.getId(), 0L)
            ))
            .toList();
    }

    private static OrderSpecifier<?> getOrderSpecifier(final Sort.Order order, final ProjectSearchOrder searchOrder) {
        final Order converted = convertTo(order);
        return switch (searchOrder) {
            case CREATED_AT -> new OrderSpecifier<>(converted, project.createdAt);
            case PAYMENT -> new OrderSpecifier<>(converted, project.payment);
            case APPLICATION_DURATION -> new OrderSpecifier<>(converted, project.applicationDuration.endDate);
            case PROJECT_ID -> new OrderSpecifier<>(converted, project.id);
        };
    }

    private static OrderSpecifier<?>[] getOrderSpecifiers(final Sort sort) {
        return sort.stream()
            .map(order -> getOrderSpecifier(order, ProjectSearchOrder.get(order.getProperty())))
            .toArray(OrderSpecifier[]::new);
    }

    @Override
    public Page<ProjectCardResponse> findBy(
        final Pageable pageable,
        final ProjectSearchFilter filter,
        final MemberId memberId
    ) {
        final JPAQuery<ProjectJpaEntity> contentQuery = buildQuery(
            queryFactory.selectFrom(project),
            filter,
            memberId
        );
        contentQuery.orderBy(getOrderSpecifiers(pageable.getSort()));
        final List<Project> projects = contentQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(persistenceMapper::mapToDomain)
            .toList();
        final ProjectId[] projectIds = toProjectIds(projects);
        final Map<ProjectId, Long> projectApplicationCounts = countProjectApplicationPort.countBy(projectIds);
        final List<ProjectCardResponse> content = assemble(projects, projectApplicationCounts);
        return paginate(
            pageable,
            content,
            countQuery -> buildQuery(countQuery.select(project.count()).from(project), filter, memberId)
        );
    }

    private ProjectJpaEntity fetchProject(final ProjectId projectId) {
        return Optional.ofNullable(
            queryFactory.selectFrom(project)
                .where(project.id.eq(projectId.getValue()))
                .fetchOne()
            )
            .orElseThrow(NotFoundProjectException::new);
    }

    @Override
    public ProjectDetailResponse findBy(final Member member, final ProjectId projectId) {
        final ProjectJpaEntity result = fetchProject(projectId);
        final Long applicantsCount = countProjectApplicationPort.countBy(projectId);
        final Long likesCount = countProjectLikePort.countBy(projectId);
        final ProjectClientResponse projectClientResponse = clientQuery.findProjectClientBy(new ClientId(result.getClientId()));
        final Boolean liked = checkProjectLikePort.existsBy(member, projectId);
        
        return queryMapper.mapToDetailResponse(result, applicantsCount, likesCount, liked, projectClientResponse);
    }
}
