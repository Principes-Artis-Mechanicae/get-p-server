package es.princip.getp.domain.serviceTerm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceTermTag implements Serializable {

    @Column(name = "service_term_tag", nullable = false)
    private String value;

    private ServiceTermTag(final String value) {
        this.value = value;
    }

    public static ServiceTermTag of(final String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("ServiceTermTag value must not be null or empty");
        }
        return new ServiceTermTag(value);
    }
}
