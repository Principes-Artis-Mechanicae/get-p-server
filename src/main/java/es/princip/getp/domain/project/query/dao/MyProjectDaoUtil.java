package es.princip.getp.domain.project.query.dao;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import es.princip.getp.domain.project.query.dto.MyProjectSearchOrder;
import org.springframework.data.domain.Sort;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
import static es.princip.getp.domain.project.command.domain.QProject.project;

class MyProjectDaoUtil {

    private static Order convertTo(final Sort.Order order) {
        return order.isAscending() ? ASC : DESC;
    }

    private static OrderSpecifier<?> toOrderSpecifier(final Sort.Order order, final MyProjectSearchOrder searchOrder) {
        final Order converted = convertTo(order);
        return switch (searchOrder) {
            case CREATED_AT -> new OrderSpecifier<>(converted, project.createdAt);
            case PAYMENT -> new OrderSpecifier<>(converted, project.payment);
            case APPLICATION_DURATION -> new OrderSpecifier<>(converted, project.applicationDuration.endDate);
            case PROJECT_ID -> new OrderSpecifier<>(converted, project.projectId);
        };
    }

    static OrderSpecifier<?>[] orderSpecifiersFromSort(Sort sort) {
        return sort.stream()
            .map(order -> toOrderSpecifier(order, MyProjectSearchOrder.get(order.getProperty())))
            .toArray(OrderSpecifier[]::new);
    }
}
