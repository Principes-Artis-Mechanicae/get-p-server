package es.princip.getp.domain.auth.fixture;

import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.member.entity.MemberType;
import java.util.ArrayList;

public class SignUpFixture {
    public static SignUpRequest createSignUpRequest(String email, String password) {
        return new SignUpRequest(email, password, new ArrayList<>(), MemberType.ROLE_PEOPLE);
    }
}
