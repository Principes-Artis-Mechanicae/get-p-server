package es.princip.getp.domain.people.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleHashtag;
import es.princip.getp.domain.people.domain.PeopleOrder;
import es.princip.getp.domain.people.domain.PeopleType;
import es.princip.getp.domain.people.dto.response.people.CardPeopleResponse;
import es.princip.getp.domain.people.dto.response.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.global.domain.Hashtag;
import es.princip.getp.global.support.QueryDslRepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
import static es.princip.getp.domain.people.domain.QPeople.people;
import static es.princip.getp.domain.people.domain.QPeopleHashtag.peopleHashtag;
import static es.princip.getp.domain.people.domain.QPeopleProfile.peopleProfile;

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

    /**
     * TODO: 페이지네이션 최적화를 위한 커서 기반 코드
     *
     * @param lastPeopleId
     * @param size
     * @return
     */
    public List<CardPeopleResponse> getCardPeopleContent_cursor(Long lastPeopleId, int size) {
        List<Tuple> result = getQueryFactory()
            .select(
                people.peopleId,
                people.peopleType,
                peopleProfile.activityArea
            )
            .from(people)
            .join(people.profile, peopleProfile)
            .where(lastPeopleId == null ? null : people.peopleId.lt(lastPeopleId))
            .orderBy(people.peopleId.desc())
            .limit(size)
            .fetch();
        return null;
    }

    private List<Hashtag> getHashtagsForPeople(Long peopleId) {
        return selectFrom(peopleHashtag)
            .where(peopleHashtag.peopleProfile.people.peopleId.eq(peopleId))
            .fetch()
            .stream()
            .map(PeopleHashtag::getHashtag)
            .toList();
    }

    private List<CardPeopleResponse> getCardPeopleContent(Pageable pageable) {
        List<Tuple> result = getQueryFactory()
            .select(
                people.peopleId,
                people.peopleType,
                peopleProfile.activityArea
            )
            .from(people)
            .join(people.profile, peopleProfile)
            .orderBy(getPeopleOrderSpecifiers(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return result.stream().map(tuple -> {
            Long peopleId = tuple.get(people.peopleId);
            String nickname = tuple.get(people.member.nickname);
            PeopleType peopleType = tuple.get(people.peopleType);
            String profileImageUri = tuple.get(people.member.profileImageUri);
            String activityArea = tuple.get(peopleProfile.activityArea);
            List<Hashtag> hashtags = getHashtagsForPeople(peopleId);
            CardPeopleProfileResponse profile =
                CardPeopleProfileResponse.from(activityArea, hashtags);

            return CardPeopleResponse.from(peopleId, nickname, peopleType, profileImageUri,
                profile);
        }).toList();
    }

    public Page<CardPeopleResponse> findCardPeoplePage(Pageable pageable) {
        return applyPagination(pageable,
            getCardPeopleContent(pageable),
            countQuery -> countQuery.select(people.count()).from(people));
    }
}
