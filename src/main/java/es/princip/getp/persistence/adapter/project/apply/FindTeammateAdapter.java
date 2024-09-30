package es.princip.getp.persistence.adapter.project.apply;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import es.princip.getp.api.controller.project.query.dto.SearchTeammateResponse;
import es.princip.getp.application.project.apply.port.out.FindTeammatePort;
import es.princip.getp.application.support.Cursor;
import es.princip.getp.application.support.CursorPageable;
import es.princip.getp.persistence.adapter.member.QMemberJpaEntity;
import es.princip.getp.persistence.adapter.people.model.QPeopleJpaEntity;
import es.princip.getp.persistence.support.QueryDslSupport;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

import static es.princip.getp.persistence.adapter.people.PeoplePersistenceUtil.getOrderSpecifier;

@Repository
@RequiredArgsConstructor
class FindTeammateAdapter extends QueryDslSupport implements FindTeammatePort {

    private static final QPeopleJpaEntity people = QPeopleJpaEntity.peopleJpaEntity;
    private static final QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;

    private <T> JPAQuery<T> buildQuery(
        final JPAQuery<T> select,
        final String nickname
    ) {
        final JPAQuery<T> query = select.from(people)
            .join(member).on(people.memberId.eq(member.id))
            .where(people.profile.isNotNull());
        if (!StringUtils.isAllBlank(nickname)) {
            query.where(member.nickname.startsWith(nickname));
        }
        return query;
    }

    private BooleanExpression getCursorBooleanExpression(final CursorPageable<Cursor> pageable) {
        if (!pageable.hasCursor()) {
            return null;
        }
        final Long idCursor = pageable.getCursor().getId();
        return people.id.lt(idCursor);
    }

    private List<SearchTeammateResponse> fetchContent(
        final CursorPageable<Cursor> pageable,
        final String nickname
    ) {
        final JPAQuery<SearchTeammateResponse> selectFrom = queryFactory.select(
            Projections.constructor(
                SearchTeammateResponse.class,
                people.id,
                member.nickname,
                member.profileImage
            )
        );
        final Sort.Order order = pageable.getSort()
            .stream()
            .findFirst()
            .orElse(null);
        return buildQuery(selectFrom, nickname)
            .where(getCursorBooleanExpression(pageable))
            .orderBy(getOrderSpecifier(order))
            .limit(pageable.getPageSize() + 1)
            .fetch();
    }

    @Override
    public Slice<SearchTeammateResponse> findBy(final CursorPageable<Cursor> pageable, final String nickname) {
        final List<SearchTeammateResponse> content = fetchContent(pageable, nickname);
        final int size = pageable.getPageSize();
        boolean hasNext = false;
        if (content.size() > size) {
            content.remove(size);
            hasNext = true;
        }
        return new SliceImpl<>(content, PageRequest.ofSize(size), hasNext);
    }
}