package es.princip.getp.fixture;

import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.member.domain.enums.MemberType;
import java.util.ArrayList;

public class SignUpFixture {
    public static SignUpRequest createSignUpRequest(String email, String password, MemberType memberType) {
        return new SignUpRequest(email, password, new ArrayList<>(), memberType);
    }
}
