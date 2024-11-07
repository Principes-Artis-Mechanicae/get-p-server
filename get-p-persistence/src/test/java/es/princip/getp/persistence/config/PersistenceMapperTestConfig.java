package es.princip.getp.persistence.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {
    "es.princip.getp.persistence.adapter.common.mapper",
    "es.princip.getp.persistence.adapter.people.mapper"
})
public abstract class PersistenceMapperTestConfig {
}
