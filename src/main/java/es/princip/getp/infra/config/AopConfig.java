package es.princip.getp.infra.config;

import es.princip.getp.infra.aop.TimeTraceAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {

    @Bean
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }
}
