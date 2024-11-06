package es.princip.getp.application.common.dto.response;

public record AddressResponse(
    String zipcode,
    String street,
    String detail
) {
}