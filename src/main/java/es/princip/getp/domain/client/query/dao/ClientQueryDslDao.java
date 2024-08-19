package es.princip.getp.domain.client.query.dao;

import com.querydsl.core.Tuple;
import es.princip.getp.api.controller.client.query.dto.ClientResponse;
import es.princip.getp.common.util.QueryDslSupport;
import es.princip.getp.domain.client.exception.NotFoundClientException;
import es.princip.getp.persistence.adapter.member.QMemberJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static es.princip.getp.domain.client.command.domain.QClient.client;

@Repository
public class ClientQueryDslDao extends QueryDslSupport implements ClientDao {

    private static final QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;

    private ClientResponse toClientResponse(final Tuple result) {
        if (result == null) {
            return null;
        }

        return new ClientResponse(
            result.get(client.clientId),
            result.get(member.nickname),
            result.get(member.phoneNumber),
            result.get(client.email.value),
            result.get(member.profileImageUrl),
            result.get(client.address),
            result.get(client.bankAccount),
            result.get(client.createdAt),
            result.get(client.updatedAt)
        );
    }

    @Override
    public ClientResponse findById(final Long clientId) {
        Tuple result = queryFactory.select(
                client.clientId,
                member.nickname,
                member.phoneNumber,
                client.email,
                member.profileImageUrl,
                client.address,
                client.bankAccount,
                client.createdAt,
                client.updatedAt
            )
            .from(client)
            .join(member).on(client.memberId.eq(member.memberId))
            .where(client.clientId.eq(clientId))
            .fetchOne();

        return Optional.ofNullable(toClientResponse(result))
            .orElseThrow(NotFoundClientException::new);
    }

    @Override
    public ClientResponse findByMemberId(final Long memberId) {
        Tuple result = queryFactory.select(
                client.clientId,
                member.nickname,
                member.phoneNumber,
                client.email.value,
                member.profileImageUrl,
                client.address,
                client.bankAccount,
                client.createdAt,
                client.updatedAt
            )
            .from(client)
            .join(member).on(client.memberId.eq(member.memberId))
            .where(client.memberId.eq(memberId))
            .fetchOne();

        return Optional.ofNullable(toClientResponse(result))
            .orElseThrow(NotFoundClientException::new);
    }
}
