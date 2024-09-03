package es.princip.getp.persistence.adapter.people;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import es.princip.getp.domain.people.model.PeopleOrder;
import es.princip.getp.persistence.adapter.people.model.PeopleJpaEntity;
import es.princip.getp.persistence.adapter.people.model.QPeopleJpaEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;

class PeopleQueryUtil {

    private static final QPeopleJpaEntity people = QPeopleJpaEntity.peopleJpaEntity;

    private static Order convertTo(final Sort.Order order) {
        return order.isAscending() ? ASC : DESC;
    }

    private static OrderSpecifier<?> getPeopleOrderSpecifier(final Sort.Order order, final PeopleOrder peopleOrder) {
        final Order converted = convertTo(order);
        return switch (peopleOrder) {
            // TODO: case INTEREST_COUNT
            case CREATED_AT -> new OrderSpecifier<>(converted, people.createdAt);
            default -> new OrderSpecifier<>(converted, people.id);
        };
    }

    static OrderSpecifier<?>[] orderSpecifiersFromSort(Sort sort) {
        return sort.stream()
            .map(order -> getPeopleOrderSpecifier(order, PeopleOrder.get(order.getProperty())))
            .toArray(OrderSpecifier[]::new);
    }

    static Long[] toPeopleIds(final List<PeopleJpaEntity> peoples) {
        return peoples.stream()
            .map(PeopleJpaEntity::getId)
            .toArray(Long[]::new);
    }
}
