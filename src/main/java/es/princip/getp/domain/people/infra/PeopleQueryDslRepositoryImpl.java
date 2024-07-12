package es.princip.getp.domain.people.infra;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleOrder;
import es.princip.getp.domain.people.presentation.dto.response.people.CardPeopleResponse;
import es.princip.getp.infra.support.QueryDslRepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
import static es.princip.getp.domain.member.domain.model.QMember.member;
import static es.princip.getp.domain.people.domain.QPeople.people;

public class PeopleQueryDslRepositoryImpl extends QueryDslRepositorySupport implements
    PeopleQueryDslRepository {

    public PeopleQueryDslRepositoryImpl() {
        super(People.class);
    }

    private OrderSpecifier<?> getOrderSpecifier(Order order, PeopleOrder peopleOrder) {
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(DESC, people.peopleId);
        switch (peopleOrder) {
            case INTEREST_COUNT:
                // TODO: 관심 수에 대하여 정렬하는 로직 추가
                break;
            case CREATED_AT:
                orderSpecifier = new OrderSpecifier<>(order.isAscending() ? ASC : DESC,
                    people.createdAt);
                break;
            default:
                break;
        }
        return orderSpecifier;
    }

    private OrderSpecifier<?>[] getPeopleOrderSpecifiers(Sort sort) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        sort.stream().forEach(order -> {
            PeopleOrder peopleOrder = PeopleOrder.get(order.getProperty());
            OrderSpecifier<?> orderSpecifier = getOrderSpecifier(order, peopleOrder);
            orderSpecifiers.add(orderSpecifier);
        });
        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }

    private List<CardPeopleResponse> getCardPeopleContent(Pageable pageable) {
        List<Tuple> result = getQueryFactory()
            .select(people, member)
            .from(people)
            .join(member).on(people.memberId.eq(member.memberId))
            .orderBy(getPeopleOrderSpecifiers(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return result.stream()
            .map(tuple -> CardPeopleResponse.from(tuple.get(people), tuple.get(member)))
            .toList();
    }

    public Page<CardPeopleResponse> findCardPeoplePage(Pageable pageable) {
        return applyPagination(pageable,
            getCardPeopleContent(pageable),
            countQuery -> countQuery.select(people.count()).from(people));
    }
}
