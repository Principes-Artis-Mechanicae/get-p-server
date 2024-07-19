package es.princip.getp.infra.config;

import es.princip.getp.infra.DataLoader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;

import java.util.List;

@TestConfiguration
@RequiredArgsConstructor
public class DataLoaderConfig {

    private final List<DataLoader> dataLoaders;

    public static final int TEST_SIZE = 100;

    @PostConstruct
    public void load() {
        dataLoaders.forEach(dataLoader -> dataLoader.load(TEST_SIZE));
    }
}
