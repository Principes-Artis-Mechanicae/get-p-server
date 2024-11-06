package es.princip.getp.api.controller.client;

import es.princip.getp.application.common.dto.response.AddressResponse;

public class AddressFixture {

    public static AddressResponse addressResponse() {
        return new AddressResponse(ZIPCODE, STREET, DETAIL);
    }
}
