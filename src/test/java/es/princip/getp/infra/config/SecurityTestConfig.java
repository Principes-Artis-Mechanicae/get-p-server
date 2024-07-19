package es.princip.getp.infra.config;

import es.princip.getp.infra.security.provider.JwtTokenProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class SecurityTestConfig {

    @MockBean
    private JwtTokenProvider jwtTokenProvider;
}
