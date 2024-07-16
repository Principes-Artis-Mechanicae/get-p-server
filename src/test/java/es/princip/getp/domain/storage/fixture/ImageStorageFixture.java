package es.princip.getp.domain.storage.fixture;

import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageStorageFixture {

    public static final Path STORAGE_PATH = Paths.get("test/storage")
        .normalize()
        .toAbsolutePath();

    public static final Path IMAGE_STORAGE_PATH =  STORAGE_PATH.resolve("images");

    public static MockMultipartFile createImageMultiPartFile() {
        return new MockMultipartFile(
            "image",
            new byte[] {0x00, 0x01, 0x02, 0x03}
        );
    }
}
