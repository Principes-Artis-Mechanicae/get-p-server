//package es.princip.getp.domain.serviceTerm.fixture;
//
//import es.princip.getp.domain.auth.presentation.dto.request.ServiceTermAgreementRequest;
//import es.princip.getp.domain.serviceTerm.dto.response.ServiceTermAgreementResponse;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//public class ServiceTermFixture {
//
//    private static final String TAG = "GET-P 서비스 약관";
//    private static final boolean AGREED = true;
//    private static final boolean REQUIRED = true;
//    private static final boolean REVOCABLE = false;
//    private static final LocalDateTime AGREED_AT = LocalDateTime.now();
//
//    public static ServiceTermAgreementRequest serviceTermAgreementRequest() {
//        return new ServiceTermAgreementRequest(TAG, AGREED);
//    }
//
//    public static ServiceTermAgreementResponse toServiceTermAgreementResponse(
//        final ServiceTermAgreementRequest request
//    ) {
//        return new ServiceTermAgreementResponse(
//            request.tag(),
//            REQUIRED,
//            request.agreed(),
//            REVOCABLE,
//            AGREED_AT
//        );
//    }
//
//    public static List<ServiceTermAgreementResponse> toServiceTermAgreementResponse(
//        final List<ServiceTermAgreementRequest> request
//    ) {
//        return request.stream().map(ServiceTermFixture::toServiceTermAgreementResponse).toList();
//    }
//}
