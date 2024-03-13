package es.princip.getp.fixture;

import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.domain.serviceTerm.dto.reqeust.ServiceTermAgreementRequest;
import java.util.List;

public class SignUpFixture {
    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "password1234!";
    private static final String VERIFICATION_CODE = "1234";
    private static final List<ServiceTermAgreementRequest> SERVICE_TERMS =
        List.of(ServiceTermFixture.createServiceTermAgreementRequest());

    public static SignUpRequest createSignUpRequest(
        String email,
        String password,
        String verificationCode,
        List<ServiceTermAgreementRequest> serviceTerms,
        MemberType memberType) {
        return new SignUpRequest(email, password, verificationCode, serviceTerms, memberType);
    }

    public static SignUpRequest createSignUpRequest(
        String email,
        String password,
        MemberType memberType) {
        return new SignUpRequest(email, password, VERIFICATION_CODE, SERVICE_TERMS, memberType);
    }

    public static SignUpRequest createSignUpRequest(MemberType memberType) {
        return new SignUpRequest(EMAIL, PASSWORD, VERIFICATION_CODE, SERVICE_TERMS, memberType);
    }
}
