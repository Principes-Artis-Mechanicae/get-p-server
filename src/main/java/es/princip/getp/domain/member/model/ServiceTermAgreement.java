package es.princip.getp.domain.member.model;

import es.princip.getp.domain.serviceTerm.domain.ServiceTermTag;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ServiceTermAgreement {

    private final ServiceTermTag tag;
    private final boolean agreed;
    private final LocalDateTime agreedAt;

    public static ServiceTermAgreement of(final ServiceTermTag tag, final boolean agreed) {
        return new ServiceTermAgreement(tag, agreed, LocalDateTime.now());
    }
}
