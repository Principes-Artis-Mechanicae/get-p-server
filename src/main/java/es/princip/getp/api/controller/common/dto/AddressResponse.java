package es.princip.getp.api.controller.common.dto;

public record AddressResponse(
    String zipcode,
    String street,
    String detail
) {
}