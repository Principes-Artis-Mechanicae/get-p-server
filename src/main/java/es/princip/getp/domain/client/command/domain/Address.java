package es.princip.getp.domain.client.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Column(name = "zipcode")
    @NotBlank
    private String zipcode;

    @Column(name = "street")
    @NotBlank
    private String street;

    @Column(name = "detail")
    private String detail;

    private Address(String zipcode, String street, String detail) {
        this.zipcode = zipcode;
        this.street = street;
        this.detail = detail;
    }

    public static Address of(String zipcode, String street, String detail) {
        return new Address(zipcode, street, detail);
    }
}
