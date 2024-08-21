package es.princip.getp.persistence.adapter.client;

import com.querydsl.core.Tuple;
import es.princip.getp.api.controller.client.query.dto.ClientResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectClientResponse;
import es.princip.getp.application.client.port.out.ClientQuery;
import es.princip.getp.common.util.QueryDslSupport;
import es.princip.getp.persistence.adapter.member.QMemberJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ClientQueryDslQuery extends QueryDslSupport implements ClientQuery {

    private final ClientPersistenceMapper mapper;

    private static final QClientJpaEntity client = QClientJpaEntity.clientJpaEntity;
    private static final QMemberJpaEntity member = QMemberJpaEntity.memberJpaEntity;

    private void validateTuple(final Tuple result) {
        if (result == null) {
            throw new NotFoundClientException();
        }
    }

    private ClientResponse mapToClientResponse(final Tuple result) {
        return new ClientResponse(
            result.get(client.clientId),
            result.get(member.nickname),
            result.get(member.phoneNumber),
            result.get(client.email),
            result.get(member.profileImage),
            mapper.mapToDomain(result.get(client.address)),
            mapper.mapToDomain(result.get(client.bankAccount)),
            result.get(client.createdAt),
            result.get(client.updatedAt)
        );
    }

    @Override
    public ClientResponse findClientById(final Long clientId) {
        final Tuple result = queryFactory.select(
                client.clientId,
                member.nickname,
                member.phoneNumber,
                client.email,
                member.profileImage,
                client.address,
                client.bankAccount,
                client.createdAt,
                client.updatedAt
            )
            .from(client)
            .join(member).on(client.memberId.eq(member.memberId))
            .where(client.clientId.eq(clientId))
            .fetchOne();
        validateTuple(result);
        return mapToClientResponse(result);
    }

    @Override
    public ClientResponse findClientByMemberId(final Long memberId) {
        final Tuple result = queryFactory.select(
                client.clientId,
                member.nickname,
                member.phoneNumber,
                client.email,
                member.profileImage,
                client.address,
                client.bankAccount,
                client.createdAt,
                client.updatedAt
            )
            .from(client)
            .join(member).on(client.memberId.eq(member.memberId))
            .where(client.memberId.eq(memberId))
            .fetchOne();
        validateTuple(result);
        return mapToClientResponse(result);
    }

    @Override
    public ProjectClientResponse findProjectClientById(final Long clientId) {
        final Tuple result = queryFactory.select(
                client.clientId,
                member.nickname,
                client.address
            )
            .from(client)
            .join(member).on(client.memberId.eq(member.memberId))
            .where(client.clientId.eq(clientId))
            .fetchOne();
        validateTuple(result);
        return new ProjectClientResponse(
            result.get(client.clientId),
            result.get(member.nickname),
            mapper.mapToDomain(result.get(client.address))
        );
    }
}
