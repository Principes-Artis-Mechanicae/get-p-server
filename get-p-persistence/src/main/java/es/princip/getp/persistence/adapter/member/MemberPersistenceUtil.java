package es.princip.getp.persistence.adapter.member;

import com.querydsl.core.types.dsl.BooleanExpression;
import es.princip.getp.domain.member.model.MemberId;

import java.util.Optional;

public class MemberPersistenceUtil {

    private static final QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;

    public static BooleanExpression memberIdEq(final MemberId memberId) {
        return Optional.ofNullable(memberId)
            .map(MemberId::getValue)
            .map(member.id::eq)
            .orElse(null);
    }
}