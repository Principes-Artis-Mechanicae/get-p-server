package es.princip.getp.persistence.adapter;

import es.princip.getp.persistence.config.PersistenceMapperTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ActiveProfiles("test")
@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = PersistenceMapperTestConfig.class)
public abstract class PersistenceMapperTest {
}
