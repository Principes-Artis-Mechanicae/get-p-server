package es.princip.getp.domain.people.query.dao;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import es.princip.getp.domain.people.command.domain.PeopleOrder;
import org.springframework.data.domain.Sort;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
import static es.princip.getp.domain.people.command.domain.QPeople.people;

class PeoplePaginationHelper {

    private static Order convertTo(final Sort.Order order) {
        return order.isAscending() ? ASC : DESC;
    }

    private static OrderSpecifier<?> getPeopleOrderSpecifier(final Sort.Order order, final PeopleOrder peopleOrder) {
        final Order converted = convertTo(order);
        return switch (peopleOrder) {
            // TODO: case INTEREST_COUNT
            case CREATED_AT -> new OrderSpecifier<>(converted, people.createdAt);
            default -> new OrderSpecifier<>(converted, people.peopleId);
        };
    }

    static OrderSpecifier<?>[] getPeopleOrderSpecifiers(Sort sort) {
        return sort.stream()
            .map(order -> getPeopleOrderSpecifier(order, PeopleOrder.get(order.getProperty())))
            .toArray(OrderSpecifier[]::new);
    }
}
