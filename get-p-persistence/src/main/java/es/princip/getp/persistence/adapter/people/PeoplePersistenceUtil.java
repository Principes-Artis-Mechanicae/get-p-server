package es.princip.getp.persistence.adapter.people;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import es.princip.getp.application.people.dto.command.PeopleSearchOrder;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.persistence.adapter.people.model.PeopleJpaEntity;
import es.princip.getp.persistence.adapter.people.model.QPeopleJpaEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;

public class PeoplePersistenceUtil {

    private static final QPeopleJpaEntity people = QPeopleJpaEntity.peopleJpaEntity;

    public static PeopleId[] getPeopleIds(final List<PeopleJpaEntity> peopleList) {
        return peopleList.stream()
            .map(PeopleJpaEntity::getId)
            .map(PeopleId::new)
            .toArray(PeopleId[]::new);
    }

    public static OrderSpecifier<?> getOrderSpecifier(final Sort.Order order) {
        if (order == null) {
            return new OrderSpecifier<>(DESC, people.id);
        }
        final String property = order.getProperty();
        final Order converted = order.isAscending() ? ASC : DESC;
        return switch (PeopleSearchOrder.from(property)) {
            case CREATED_AT -> new OrderSpecifier<>(converted, people.createdAt);
            default -> new OrderSpecifier<>(converted, people.id);
        };
    }
}