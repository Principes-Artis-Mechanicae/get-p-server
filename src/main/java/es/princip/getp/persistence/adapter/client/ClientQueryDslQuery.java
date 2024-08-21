package es.princip.getp.persistence.adapter.client;

import com.querydsl.core.Tuple;
import es.princip.getp.api.controller.client.query.dto.ClientResponse;
import es.princip.getp.application.client.port.out.ClientQuery;
import es.princip.getp.common.util.QueryDslSupport;
import es.princip.getp.persistence.adapter.member.QMemberJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class ClientQueryDslQuery extends QueryDslSupport implements ClientQuery {

    private final ClientPersistenceMapper mapper;

    private static final QClientJpaEntity client = QClientJpaEntity.clientJpaEntity;
    private static final QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;

    private ClientResponse mapToResponse(final Tuple result) {
        if (result == null) {
            return null;
        }

        return new ClientResponse(
            result.get(client.clientId),
            result.get(member.nickname),
            result.get(member.phoneNumber),
            result.get(client.email),
            result.get(member.profileImageUrl),
            mapper.mapToDomain(result.get(client.address)),
            mapper.mapToDomain(result.get(client.bankAccount)),
            result.get(client.createdAt),
            result.get(client.updatedAt)
        );
    }

    @Override
    public ClientResponse findById(final Long clientId) {
        final Tuple result = queryFactory.select(
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

        return Optional.ofNullable(mapToResponse(result))
            .orElseThrow(NotFoundClientException::new);
    }

    @Override
    public ClientResponse findByMemberId(final Long memberId) {
        final Tuple result = queryFactory.select(
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
            .where(client.memberId.eq(memberId))
            .fetchOne();

        return Optional.ofNullable(mapToResponse(result))
            .orElseThrow(NotFoundClientException::new);
    }
}
