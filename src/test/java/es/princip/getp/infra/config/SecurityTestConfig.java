package es.princip.getp.infra.config;

import es.princip.getp.domain.auth.application.AccessTokenService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class SecurityTestConfig {

    @MockBean
    private AccessTokenService accessTokenService;
}
