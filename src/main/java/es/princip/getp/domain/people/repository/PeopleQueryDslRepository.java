package es.princip.getp.domain.people.repository;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.domain.people.entity.QPeople;
import es.princip.getp.domain.people.enums.PeopleOrder;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PeopleQueryDslRepository {
    private final JPAQueryFactory queryFactory;
    private QPeople people = QPeople.people;

    private OrderSpecifier<?> getOrderSpecifier(org.springframework.data.domain.Sort.Order order, PeopleOrder peopleOrder) {
        OrderSpecifier<?> orderSpecifier;
        if (order.isAscending()) {
            orderSpecifier = new OrderSpecifier<>(Order.ASC, people.createdAt);
        } else {
            orderSpecifier = new OrderSpecifier<>(Order.DESC, people.createdAt);
        }
        return orderSpecifier;
    }

    private OrderSpecifier<?>[] getPeopleOrderSpecifiers(Sort sort) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        sort.stream().forEach(order -> {
            PeopleOrder peopleOrder = PeopleOrder.valueOf(order.getProperty());
            OrderSpecifier<?> orderSpecifier = getOrderSpecifier(order, peopleOrder);

            switch (peopleOrder) {
                case CREATED_AT:
                    orderSpecifiers.add(orderSpecifier);
                default:
                    orderSpecifiers.add(orderSpecifier);
            }
        });
        return orderSpecifiers.stream().toArray(OrderSpecifier[]::new);
    }

    private List<People> getPeopleContent(Pageable pageable) {
        return queryFactory.selectFrom(people).orderBy(getPeopleOrderSpecifiers(pageable.getSort()))
                .offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
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
