package es.princip.getp.domain.client.query.dao;

import com.querydsl.core.Tuple;
import es.princip.getp.domain.client.query.dto.ClientResponse;
import es.princip.getp.infra.support.QueryDslSupport;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static es.princip.getp.domain.client.command.domain.QClient.client;
import static es.princip.getp.domain.member.command.domain.model.QMember.member;

@Repository
public class ClientDaoImpl extends QueryDslSupport implements ClientDao {

    private ClientResponse toClientResponse(final Tuple result) {
        if (result == null) {
            return null;
        }

        return new ClientResponse(
            result.get(client.clientId),
            result.get(member.nickname.value),
            result.get(member.phoneNumber.value),
            result.get(client.email.value),
            result.get(member.profileImage.uri),
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
                member.nickname.value,
                member.phoneNumber.value,
                client.email,
                member.profileImage.uri,
                client.address,
                client.bankAccount,
                client.createdAt,
                client.updatedAt
            )
            .from(client)
            .join(member).on(client.memberId.eq(member.memberId))
            .where(client.clientId.eq(clientId))
            .fetchOne();

        return Optional.ofNullable(toClientResponse(result)).orElseThrow(
            () -> new EntityNotFoundException("해당 의뢰자 정보가 존재하지 않습니다.")
        );
    }

    @Override
    public ClientResponse findByMemberId(final Long memberId) {
        Tuple result = queryFactory.select(
                client.clientId,
                member.nickname.value,
                member.phoneNumber.value,
                client.email.value,
                member.profileImage.uri,
                client.address,
                client.bankAccount,
                client.createdAt,
                client.updatedAt
            )
            .from(client)
            .join(member).on(client.memberId.eq(member.memberId))
            .where(client.memberId.eq(memberId))
            .fetchOne();

        return Optional.ofNullable(toClientResponse(result)).orElseThrow(
            () -> new EntityNotFoundException("해당 의뢰자 정보가 존재하지 않습니다.")
        );
    }
}
