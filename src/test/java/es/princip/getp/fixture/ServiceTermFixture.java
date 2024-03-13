package es.princip.getp.fixture;

import es.princip.getp.domain.serviceTerm.dto.reqeust.ServiceTermAgreementRequest;

public class ServiceTermFixture {

    private static final String TAG = "tag";
    private static final boolean AGREED = true;

    public static ServiceTermAgreementRequest createServiceTermAgreementRequest() {
        return new ServiceTermAgreementRequest(TAG, AGREED);
    }
}
