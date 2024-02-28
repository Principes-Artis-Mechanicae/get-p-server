package es.princip.getp.domain.people.repository;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import es.princip.getp.domain.hashtag.entity.Hashtag;
import es.princip.getp.domain.people.dto.response.people.CardPeopleResponse;
import es.princip.getp.domain.people.dto.response.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.domain.people.entity.PeopleType;
import es.princip.getp.domain.people.entity.QPeople;
import es.princip.getp.domain.people.entity.QPeopleProfile;
import es.princip.getp.domain.people.enums.PeopleOrder;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    private final QPeopleProfile peopleProfile = QPeopleProfile.peopleProfile;
    private final EntityManager em;

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

    private List<Hashtag> getHashtagsForPeople(Long peopleId) {
        String sql = "SELECT h.hashtag FROM people_hashtags h WHERE h.people_profile_id = :peopleId";
        List<String> hashtagValues = em.createNativeQuery(sql)
                                       .setParameter("peopleId", peopleId)
                                       .getResultList();
        
        return hashtagValues.stream()
                            .map(Hashtag::from)
                            .collect(Collectors.toList());
    }
    
    private List<CardPeopleResponse> getCardPeopleContent(Pageable pageable) {
        List<Tuple> result = queryFactory
            .select(
                people.peopleId,
                people.nickname,
                people.peopleType,
                people.profileImageUri,
                peopleProfile.activityArea
            )
            .from(people)
            .join(people.peopleProfile, peopleProfile)
            .orderBy(getPeopleOrderSpecifiers(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
        
        return result.stream().map(tuple -> {
            Long peopleId = tuple.get(people.peopleId);
            String nickname = tuple.get(people.nickname);
            PeopleType peopleType = tuple.get(people.peopleType);
            String profileImageUri = tuple.get(people.profileImageUri);
            String activityArea = tuple.get(peopleProfile.activityArea);
            List<Hashtag> hashtags = getHashtagsForPeople(peopleId);
            CardPeopleProfileResponse profile = new CardPeopleProfileResponse(activityArea, hashtags);

        return new CardPeopleResponse(peopleId, nickname, peopleType, profileImageUri, profile);
        }).toList();
    }

    private JPAQuery<Long> getPeopleCountQuery() {
        return queryFactory.select(people.count()).from(people);
    }
    
    public Page<CardPeopleResponse> findCardPeoplePage(Pageable pageable) {
        List<CardPeopleResponse> content = getCardPeopleContent(pageable);
        JPAQuery<Long> countQuery = getPeopleCountQuery();
        
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
