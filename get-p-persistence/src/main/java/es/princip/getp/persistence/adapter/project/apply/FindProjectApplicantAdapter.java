package es.princip.getp.persistence.adapter.project.apply;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import es.princip.getp.api.controller.project.query.dto.ProjectApplicantResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectApplicantTeammateResponse;
import es.princip.getp.application.project.apply.port.out.FindProjectApplicantPort;
import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
import es.princip.getp.domain.people.model.Education;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.persistence.adapter.member.QMemberJpaEntity;
import es.princip.getp.persistence.adapter.people.model.QPeopleJpaEntity;
import es.princip.getp.persistence.adapter.project.apply.model.QProjectApplicationJpaEntity;
import es.princip.getp.persistence.adapter.project.apply.model.QTeammateJpaEntity;
import es.princip.getp.persistence.support.QueryDslSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.querydsl.core.types.Order.DESC;
import static es.princip.getp.domain.project.apply.model.ProjectApplicationStatus.PENDING_TEAM_APPROVAL;

@Repository
@RequiredArgsConstructor
class FindProjectApplicantAdapter extends QueryDslSupport implements FindProjectApplicantPort {

    private static final QPeopleJpaEntity people = QPeopleJpaEntity.peopleJpaEntity;
    private static final QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;
    private static final QProjectApplicationJpaEntity application = QProjectApplicationJpaEntity.projectApplicationJpaEntity;
    private static final QTeammateJpaEntity teammate = QTeammateJpaEntity.teammateJpaEntity;

    private BooleanExpression getCursorBooleanExpression(final CursorPageable<? extends Cursor> pageable) {
        if (!pageable.hasCursor()) {
            return null;
        }
        final var idCursor = pageable.getCursor().getId();
        return application.id.lt(idCursor);
    }

    private List<Tuple> fetchApplicants(
        final CursorPageable<? extends Cursor> pageable,
        final ProjectId projectId
    ) {
        return queryFactory.select(
            application.id,
            application.applicantId,
            people.profile.school,
            people.profile.major,
            member.nickname,
            member.profileImage,
            application.status
        )
        .from(application)
        .join(people).on(application.applicantId.eq(people.id))
        .join(member).on(people.memberId.eq(member.id))
        .where(application.projectId.eq(projectId.getValue())
            .and(application.status.ne(PENDING_TEAM_APPROVAL))
            .and(getCursorBooleanExpression(pageable)))
        .orderBy(new OrderSpecifier<>(DESC, application.id))
        .limit(pageable.getPageSize() + 1)
        .fetch();
    }

    private Map<Long, List<ProjectApplicantTeammateResponse>> fetchTeammates(final Long[] ids) {
        return queryFactory.select(
            teammate.application.id,
            teammate.peopleId,
            member.nickname,
            member.profileImage
        )
        .from(teammate)
        .join(people).on(teammate.peopleId.eq(people.id))
        .join(member).on(people.memberId.eq(member.id))
        .where(teammate.application.id.in(ids))
        .transform(GroupBy.groupBy(teammate.application.id).as(GroupBy.list(
            Projections.constructor(
                ProjectApplicantTeammateResponse.class,
                teammate.peopleId,
                member.nickname,
                member.profileImage
            )
        )));
    }

    private List<ProjectApplicantResponse> fetchContent(
        final CursorPageable<? extends Cursor> pageable,
        final ProjectId projectId
    ) {
        final var applicants = fetchApplicants(pageable, projectId);
        final var ids = applicants.stream()
            .map(tuple -> tuple.get(application.id))
            .toArray(Long[]::new);
        final var teammates = fetchTeammates(ids);
        return applicants.stream()
            .map(tuple -> new ProjectApplicantResponse(
                tuple.get(application.applicantId),
                tuple.get(member.nickname),
                tuple.get(member.profileImage),
                Education.of(tuple.get(people.profile.school), tuple.get(people.profile.major)),
                tuple.get(application.status),
                Optional.ofNullable(teammates.get(tuple.get(application.id)))
                    .orElse(List.of())
            ))
            .toList();
    }

    @Override
    public Slice<ProjectApplicantResponse> findApplicantsBy(
        final CursorPageable<? extends Cursor> pageable,
        final ProjectId projectId
    ) {
        final List<ProjectApplicantResponse> content = fetchContent(pageable, projectId);
        return paginate(pageable, content);
    }
}