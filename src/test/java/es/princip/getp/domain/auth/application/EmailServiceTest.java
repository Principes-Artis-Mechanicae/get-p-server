package es.princip.getp.domain.auth.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @Nested
    @DisplayName("sendEmail()은")
    class SendEmail {
        @DisplayName("이메일을 전송한다.")
        @Test
        void sendEmail() {
        }
    }
}
