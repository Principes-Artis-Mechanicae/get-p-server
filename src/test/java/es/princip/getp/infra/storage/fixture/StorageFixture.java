package es.princip.getp.infra.storage.fixture;

import java.nio.file.Path;
import java.nio.file.Paths;

public class StorageFixture {

    public static final String BASE_URL = "https://storage.princip.es/";

    public static final String STORAGE_PATH_STR = "src/test/resources/static/";

    public static final Path STORAGE_PATH = Paths.get(STORAGE_PATH_STR)
        .normalize()
        .toAbsolutePath();
}
