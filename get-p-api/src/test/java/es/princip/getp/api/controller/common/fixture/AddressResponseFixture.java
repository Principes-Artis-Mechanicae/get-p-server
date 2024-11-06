package es.princip.getp.api.controller.common.fixture;

import es.princip.getp.application.common.dto.response.AddressResponse;
import es.princip.getp.domain.client.model.Address;

import static es.princip.getp.fixture.client.AddressFixture.address;

public class AddressResponseFixture {

    public static AddressResponse addressResponse() {
        final Address address = address();
        return new AddressResponse(
            address.getZipcode(),
            address.getStreet(),
            address.getDetail()
        );
    }
}
