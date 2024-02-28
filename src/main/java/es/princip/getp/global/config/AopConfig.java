package es.princip.getp.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.princip.getp.global.aop.TimeTraceAop;

@Configuration
public class AopConfig {
    @Bean
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }
}
