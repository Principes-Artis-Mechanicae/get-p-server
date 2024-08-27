package es.princip.getp.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
@Execution(ExecutionMode.SAME_THREAD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ComponentScan(basePackages = {
    "es.princip.getp.domain.client.query.dao",
    "es.princip.getp.domain.like.query.dao",
    "es.princip.getp.domain.people.query.dao",
    "es.princip.getp.domain.project.query.dao",
    "es.princip.getp.persistence.adapter"
}) // TODO: DAO를 persistence adapter로 변경할 것. 애플리케이션 컨텍스트 캐싱을 위해 임시로 추가
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class DaoTest {
}
