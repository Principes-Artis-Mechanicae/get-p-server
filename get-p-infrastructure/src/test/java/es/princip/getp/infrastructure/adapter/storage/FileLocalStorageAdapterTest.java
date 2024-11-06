package es.princip.getp.infrastructure.adapter.storage;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.file.Path;

import static es.princip.getp.fixture.storage.StorageFixture.BASE_URI;
import static es.princip.getp.fixture.storage.StorageFixture.STORAGE_PATH;
import static es.princip.getp.infrastructure.adapter.storage.fixure.MultipartFileFixture.fileMultiPartFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatPath;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileLocalStorageAdapterTest {

    private final FileLocalStorageAdapter adapter = new FileLocalStorageAdapter(BASE_URI, STORAGE_PATH);

    private final MultipartFile file = fileMultiPartFile();
    private final String filePath = "1/files/1/test.txt";
    private final Path destination = Path.of(filePath);

    @Test
    @Order(1)
    void 파일을_로컬_스토리지에_저장한다() {
        final URI fileUri = adapter.store(file, destination);

        assertThatPath(Path.of(STORAGE_PATH, filePath)).exists();
        assertThat(fileUri).isEqualTo(URI.create(BASE_URI).resolve(filePath));
    }

    @Test
    @Order(2)
    void 파일을_로컬_스토리지에서_삭제한다() {
        final URI fileUri = adapter.store(file, destination);

        adapter.delete(fileUri);

        assertThatPath(Path.of(STORAGE_PATH, filePath)).doesNotExist();
    }
}