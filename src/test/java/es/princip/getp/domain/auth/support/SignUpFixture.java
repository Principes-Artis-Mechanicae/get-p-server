package es.princip.getp.domain.auth.support;

import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.auth.dto.response.SignUpResponse;
import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.domain.serviceTerm.dto.reqeust.ServiceTermAgreementRequest;
import es.princip.getp.domain.serviceTerm.support.ServiceTermFixture;

import java.util.List;

public class SignUpFixture {
    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "password1234!";
    private static final String VERIFICATION_CODE = "1234";
    private static final List<ServiceTermAgreementRequest> SERVICE_TERMS =
        List.of(ServiceTermFixture.createServiceTermAgreementRequest());

    public static SignUpRequest createSignUpRequest(
        final String email,
        final String password,
        final String verificationCode,
        final List<ServiceTermAgreementRequest> serviceTerms,
        final MemberType memberType
    ) {
        return new SignUpRequest(email, password, verificationCode, serviceTerms, memberType);
    }

    public static SignUpRequest createSignUpRequest(
        final String email,
        final String password,
        final MemberType memberType
    ) {
        return new SignUpRequest(email, password, VERIFICATION_CODE, SERVICE_TERMS, memberType);
    }

    public static SignUpRequest createSignUpRequest(final MemberType memberType) {
        return new SignUpRequest(EMAIL, PASSWORD, VERIFICATION_CODE, SERVICE_TERMS, memberType);
    }

    public static SignUpResponse toSignUpResponse(final SignUpRequest request) {
        return new SignUpResponse(
            request.email(),
            ServiceTermFixture.toServiceTermAgreementResponse(request.serviceTerms()),
            request.memberType()
        );
    }
}
