package es.princip.getp.domain.people.query.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.common.domain.TechStack;
import es.princip.getp.domain.people.command.domain.PeopleOrder;
import es.princip.getp.domain.people.command.domain.Portfolio;
import es.princip.getp.domain.people.query.dto.people.*;
import es.princip.getp.domain.people.query.dto.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.domain.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.domain.people.query.dto.peopleProfile.PortfolioResponse;
import es.princip.getp.domain.people.query.dto.peopleProfile.PublicDetailPeopleProfileResponse;
import es.princip.getp.infra.support.QueryDslSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
import static es.princip.getp.domain.member.domain.model.QMember.member;
import static es.princip.getp.domain.people.command.domain.QPeople.people;
import static es.princip.getp.domain.people.command.domain.QPeopleProfile.peopleProfile;

@Repository
public class PeopleDaoImpl extends QueryDslSupport implements PeopleDao {

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

    private List<String> fromHashtags(List<Hashtag> hashtags) {
        return hashtags.stream()
            .map(Hashtag::getValue)
            .toList();
    }

    private List<String> fromTechStacks(List<TechStack> techStacks) {
        return techStacks.stream()
            .map(TechStack::getValue)
            .toList();
    }

    private List<PortfolioResponse> fromPortfolios(List<Portfolio> portfolios) {
        return portfolios.stream()
            .map(portfolio -> new PortfolioResponse(
                portfolio.getDescription(),
                portfolio.getUrl().getValue()
            ))
            .toList();
    }

    private List<CardPeopleResponse> getCardPeopleContent(Pageable pageable) {
        List<Tuple> result = getQueryFactory()
            .select(
                people.peopleId,
                people.peopleType,
                member.nickname.value,
                member.profileImage.uri,
                peopleProfile.activityArea,
                peopleProfile.hashtags
            )
            .from(people)
            .join(member).on(people.memberId.eq(member.memberId))
            .join(peopleProfile).on(people.peopleId.eq(peopleProfile.peopleId))
            .orderBy(getPeopleOrderSpecifiers(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return result.stream()
            .map(tuple -> new CardPeopleResponse(
                tuple.get(people.peopleId),
                tuple.get(people.peopleType),
                new PeopleMemberResponse(
                    tuple.get(member.nickname.value),
                    tuple.get(member.profileImage.uri)
                ),
                new CardPeopleProfileResponse(
                    tuple.get(peopleProfile.activityArea),
                    fromHashtags(tuple.get(peopleProfile.hashtags)),
                    0,
                    0
                )
            ))
            .toList();
    }

    @Override
    public Page<CardPeopleResponse> findCardPeoplePage(Pageable pageable) {
        return applyPagination(pageable,
            getCardPeopleContent(pageable),
            countQuery -> countQuery.select(people.count()).from(people));
    }

    @Override
    public Optional<DetailPeopleResponse> findDetailPeopleById(Long peopleId) {
        Tuple result = getQueryFactory()
            .select(
                people.peopleId,
                people.peopleType,
                member.nickname.value,
                member.profileImage.uri,
                peopleProfile.introduction,
                peopleProfile.activityArea,
                peopleProfile.techStacks,
                peopleProfile.education,
                peopleProfile.hashtags,
                peopleProfile.portfolios
            )
            .from(people)
            .join(member).on(people.memberId.eq(member.memberId))
            .join(peopleProfile).on(people.peopleId.eq(peopleProfile.peopleId))
            .where(people.peopleId.eq(peopleId))
            .fetchOne();

        if (result == null) {
            return Optional.empty();
        }

        return Optional.of(new DetailPeopleResponse(
            result.get(people.peopleId),
            result.get(people.peopleType),
            new PeopleMemberResponse(
                result.get(member.nickname.value),
                result.get(member.profileImage.uri)
            ),
            new DetailPeopleProfileResponse(
                result.get(peopleProfile.introduction),
                result.get(peopleProfile.activityArea),
                fromTechStacks(result.get(peopleProfile.techStacks)),
                result.get(peopleProfile.education),
                fromHashtags(result.get(peopleProfile.hashtags)),
                0,
                0,
                fromPortfolios(result.get(peopleProfile.portfolios))
            )
        ));
    }

    @Override
    public Optional<PublicDetailPeopleResponse> findPublicDetailPeopleById(Long peopleId) {
        Tuple result = getQueryFactory()
            .select(
                people.peopleId,
                people.peopleType,
                member.nickname.value,
                member.profileImage.uri,
                peopleProfile.hashtags
            )
            .from(people)
            .join(member).on(people.memberId.eq(member.memberId))
            .join(peopleProfile).on(people.peopleId.eq(peopleProfile.peopleId))
            .where(people.peopleId.eq(peopleId))
            .fetchOne();

        if (result == null) {
            return Optional.empty();
        }

        return Optional.of(new PublicDetailPeopleResponse(
            result.get(people.peopleId),
            result.get(people.peopleType),
            new PeopleMemberResponse(
                result.get(member.nickname.value),
                result.get(member.profileImage.uri)
            ),
            new PublicDetailPeopleProfileResponse(
                fromHashtags(result.get(peopleProfile.hashtags)),
                0,
                0
            )
        ));
    }

    @Override
    public Optional<PeopleResponse> findByMemberId(final Long memberId) {
        Tuple result = getQueryFactory()
            .select(
                people.peopleId,
                people.email.value,
                people.peopleType,
                people.createdAt,
                people.updatedAt
            )
            .from(people)
            .where(people.memberId.eq(memberId))
            .fetchOne();

        if (result == null) {
            return Optional.empty();
        }

        return Optional.of(new PeopleResponse(
            result.get(people.peopleId),
            result.get(people.email.value),
            result.get(people.peopleType),
            result.get(people.createdAt),
            result.get(people.updatedAt)
        ));
    }

    @Override
    public Optional<DetailPeopleProfileResponse> findDetailPeopleProfileByMemberId(final Long memberId) {
        Tuple result = getQueryFactory()
            .select(
                peopleProfile.introduction,
                peopleProfile.activityArea,
                peopleProfile.techStacks,
                peopleProfile.education,
                peopleProfile.hashtags,
                peopleProfile.portfolios
            )
            .from(peopleProfile)
            .join(people).on(peopleProfile.peopleId.eq(people.peopleId))
            .where(people.peopleId.eq(memberId))
            .fetchOne();

        if (result == null) {
            return Optional.empty();
        }

        return Optional.of(new DetailPeopleProfileResponse(
            result.get(peopleProfile.introduction),
            result.get(peopleProfile.activityArea),
            fromTechStacks(result.get(peopleProfile.techStacks)),
            result.get(peopleProfile.education),
            fromHashtags(result.get(peopleProfile.hashtags)),
            0,
            0,
            fromPortfolios(result.get(peopleProfile.portfolios))
        ));
    }
}
