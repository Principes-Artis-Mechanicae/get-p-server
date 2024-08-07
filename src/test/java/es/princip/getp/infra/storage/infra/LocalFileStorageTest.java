package es.princip.getp.infra.storage.infra;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;

import static es.princip.getp.infra.storage.fixture.FileStorageFixture.DUMMY_TEXT;
import static es.princip.getp.infra.storage.fixture.StorageFixture.BASE_URI;
import static es.princip.getp.infra.storage.fixture.StorageFixture.STORAGE_PATH_STR;
import static org.assertj.core.api.Assertions.assertThat;

class LocalFileStorageTest {

    private final LocalFileStorage localFileStorage = new LocalFileStorage(BASE_URI, STORAGE_PATH_STR);

    @Test
    void 파일을_로컬_스토리지에_저장한다() throws IOException {
        final InputStream in = new ByteArrayInputStream(DUMMY_TEXT.getBytes());
        final String filePath = "1/test.txt";
        final Path destination = Path.of(filePath);

        final URI fileUri = localFileStorage.storeFile(in, destination);

        final File saved = new File(STORAGE_PATH_STR + "files/" + filePath);
        assertThat(saved).exists();
        assertThat(fileUri).isEqualTo(URI.create(BASE_URI).resolve("files/" + filePath));
    }
}