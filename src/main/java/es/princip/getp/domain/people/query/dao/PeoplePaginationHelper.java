package es.princip.getp.domain.people.query.dao;

import com.querydsl.core.types.OrderSpecifier;
import es.princip.getp.domain.people.command.domain.PeopleOrder;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
import static es.princip.getp.domain.people.command.domain.QPeople.people;

class PeoplePaginationHelper {

    private static OrderSpecifier<?> getOrderSpecifier(Sort.Order order, PeopleOrder peopleOrder) {
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

    static OrderSpecifier<?>[] getPeopleOrderSpecifiers(Sort sort) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        sort.stream().forEach(order -> {
            PeopleOrder peopleOrder = PeopleOrder.get(order.getProperty());
            OrderSpecifier<?> orderSpecifier = getOrderSpecifier(order, peopleOrder);
            orderSpecifiers.add(orderSpecifier);
        });
        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }
}
