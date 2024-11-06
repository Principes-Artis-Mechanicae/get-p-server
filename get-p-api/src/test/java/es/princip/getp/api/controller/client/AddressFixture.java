package es.princip.getp.api.controller.client;

import es.princip.getp.api.controller.common.dto.AddressResponse;
import es.princip.getp.domain.client.model.Address;

public class AddressFixture {

    public static AddressResponse addressResponse() {
        return new AddressResponse(ZIPCODE, STREET, DETAIL);
    }
}
