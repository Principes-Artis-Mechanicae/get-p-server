package es.princip.getp.domain.serviceTerm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@ToString
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceTermTag implements Serializable {

    @Column(name = "service_term_tag")
    @NotNull
    private String value;

    public ServiceTermTag(final String value) {
        this.value = value;
    }

    public static ServiceTermTag of(final String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("ServiceTermTag value must not be null or empty");
        }
        return new ServiceTermTag(value);
    }
}
