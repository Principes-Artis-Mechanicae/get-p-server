package es.princip.getp.domain.serviceTerm.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
public class ServiceTermTag {

    private final String value;

    public ServiceTermTag(final String value) {
        this.value = value;
    }

    public static ServiceTermTag of(final String value) {
        Objects.requireNonNull(value);
        return new ServiceTermTag(value);
    }
}
