package es.princip.getp.fixture.client;

import es.princip.getp.domain.client.model.Address;

public class AddressFixture {

    public static final String ZIPCODE = "41566";
    public static final String STREET = "대구광역시 북구 대학로 80";
    public static final String DETAIL = "IT대학 융복합관";

    public static Address address() {
        return new Address(ZIPCODE, STREET, DETAIL);
    }
}
