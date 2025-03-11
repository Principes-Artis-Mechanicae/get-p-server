package es.princip.getp.domain.member.model;

import es.princip.getp.domain.support.BaseModel;
import es.princip.getp.domain.serviceTerm.model.ServiceTermTag;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode(exclude = "agreedAt", callSuper = false)
public class ServiceTermAgreement extends BaseModel {

    @NotNull private final ServiceTermTag tag;
    @NotNull private final boolean agreed;
    @NotNull private final LocalDateTime agreedAt;

    public ServiceTermAgreement(
        final ServiceTermTag tag,
        final boolean agreed,
        final LocalDateTime agreedAt
    ) {
        this.tag = tag;
        this.agreed = agreed;
        this.agreedAt = agreedAt;

        validate();
    }

    static ServiceTermAgreement of(final ServiceTermTag tag, final boolean agreed) {
        return new ServiceTermAgreement(tag, agreed, LocalDateTime.now());
    }
}
