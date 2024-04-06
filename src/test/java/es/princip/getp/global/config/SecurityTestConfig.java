package es.princip.getp.global.config;

import es.princip.getp.domain.member.service.MemberService;
import es.princip.getp.global.security.provider.JwtTokenProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class SecurityTestConfig {
    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;
}