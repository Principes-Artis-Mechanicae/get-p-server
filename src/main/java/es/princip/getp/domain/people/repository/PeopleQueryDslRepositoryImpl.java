package es.princip.getp.domain.people.repository;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.domain.people.entity.QPeople;
import es.princip.getp.domain.people.enums.PeopleOrder;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PeopleQueryDslRepositoryImpl implements PeopleQueryDslRepository {

    private final JPAQueryFactory queryFactory;
    private final QPeople people = QPeople.people;

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
            PeopleOrder peopleOrder = PeopleOrder.valueOf(order.getProperty());
            OrderSpecifier<?> orderSpecifier = getOrderSpecifier(order, peopleOrder);
            orderSpecifiers.add(orderSpecifier);
        });
        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }

    private List<People> getPeopleContent(Pageable pageable) {
        return queryFactory.selectFrom(people)
            .orderBy(getPeopleOrderSpecifiers(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private JPAQuery<Long> getPeopleCountQuery() {
        return queryFactory.select(people.count()).from(people);
    }

    public Page<People> findPeoplePage(Pageable pageable) {
        List<People> content = getPeopleContent(pageable);
        JPAQuery<Long> countQuery = getPeopleCountQuery();
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
