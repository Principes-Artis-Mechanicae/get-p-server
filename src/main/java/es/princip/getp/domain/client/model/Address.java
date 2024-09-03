package es.princip.getp.domain.client.model;

import es.princip.getp.domain.common.model.BaseModel;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Address extends BaseModel {

    @NotBlank private final String zipcode;
    @NotBlank private final String street;
    private final String detail;

    public Address(
        final String zipcode,
        final String street,
        final String detail
    ) {
        this.zipcode = zipcode;
        this.street = street;
        this.detail = detail;

        validate();
    }

    @Override
    public String toString() {
        return Stream.of(zipcode, street, detail)
            .filter(part -> part != null && !part.trim().isEmpty())
            .collect(Collectors.joining(" "));
    }
}
