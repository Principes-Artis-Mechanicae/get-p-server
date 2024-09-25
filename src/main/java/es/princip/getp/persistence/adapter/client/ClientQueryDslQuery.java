package es.princip.getp.persistence.adapter.client;

import com.querydsl.core.Tuple;
import es.princip.getp.api.controller.client.query.dto.ClientResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectClientResponse;
import es.princip.getp.application.client.port.out.ClientQuery;
import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.persistence.adapter.member.QMemberJpaEntity;
import es.princip.getp.persistence.support.QueryDslSupport;
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
            result.get(client.id),
            result.get(member.nickname),
            result.get(member.phoneNumber),
            result.get(client.email),
            result.get(member.profileImage),
            mapper.mapToDomain(result.get(client.address)),
            result.get(client.createdAt),
            result.get(client.updatedAt)
        );
    }

    @Override
    public ClientResponse findClientBy(final ClientId clientId) {
        final Tuple result = queryFactory.select(
                client.id,
                member.nickname,
                member.phoneNumber,
                client.email,
                member.profileImage,
                client.address,
                client.createdAt,
                client.updatedAt
            )
            .from(client)
            .join(member).on(client.memberId.eq(member.id))
            .where(client.id.eq(clientId.getValue()))
            .fetchOne();
        validateTuple(result);
        return mapToClientResponse(result);
    }

    @Override
    public ClientResponse findClientBy(final MemberId memberId) {
        final Tuple result = queryFactory.select(
                client.id,
                member.nickname,
                member.phoneNumber,
                client.email,
                member.profileImage,
                client.address,
                client.createdAt,
                client.updatedAt
            )
            .from(client)
            .join(member).on(client.memberId.eq(member.id))
            .where(client.memberId.eq(memberId.getValue()))
            .fetchOne();
        validateTuple(result);
        return mapToClientResponse(result);
    }

    @Override
    public ProjectClientResponse findProjectClientBy(final ClientId clientId) {
        final Tuple result = queryFactory.select(
                client.id,
                member.nickname,
                client.address
            )
            .from(client)
            .join(member).on(client.memberId.eq(member.id))
            .where(client.id.eq(clientId.getValue()))
            .fetchOne();
        validateTuple(result);
        return new ProjectClientResponse(
            result.get(client.id),
            result.get(member.nickname),
            mapper.mapToDomain(result.get(client.address))
        );
    }
}
