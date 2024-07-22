package es.princip.getp.infra.support;

import es.princip.getp.infra.DataLoader;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@Execution(ExecutionMode.SAME_THREAD)
public abstract class DaoTest {

    public static final int TEST_SIZE = 100;

    @Autowired
    private List<DataLoader> dataLoaders;

    @PostConstruct
    public void init() {
        log.info("데이터베이스 더미 데이터 생성 완료");
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }
}
