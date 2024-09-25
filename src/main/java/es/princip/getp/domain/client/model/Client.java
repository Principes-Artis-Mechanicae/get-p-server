package es.princip.getp.domain.client.model;

import es.princip.getp.domain.common.model.BankAccount;
import es.princip.getp.domain.common.model.Email;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.support.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Client extends BaseEntity {

    private ClientId id;
    @NotNull private Email email;
    private Address address; //TODO: 주소기반산업지원서비스 API로 확장 예정
    private BankAccount bankAccount;
    @NotNull private MemberId memberId;

    @Builder
    public Client(
        final ClientId id,
        final Email email,
        final Address address,
        final MemberId memberId,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.id = id;
        this.email = email;
        this.address = address;
        this.memberId = memberId;

        validate();
    }

    private void setEmail(final Email email) {
        if (email == null) {
            throw new IllegalArgumentException();
        }
        this.email = email;
    }

    private void setAddress(final Address address) {
        if (address == null) {
            throw new IllegalArgumentException();
        }
        this.address = address;
    }

    public void edit(final Email email, final Address address) {
        setEmail(email);
        setAddress(address);
    }
}