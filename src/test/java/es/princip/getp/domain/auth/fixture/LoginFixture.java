package es.princip.getp.domain.auth.fixture;

import es.princip.getp.domain.auth.dto.request.LoginRequest;

public class LoginFixture {
    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "1q2w3e4r!";

    public static LoginRequest createLoginRequest() {
        return new LoginRequest(EMAIL, PASSWORD);
    }
}
