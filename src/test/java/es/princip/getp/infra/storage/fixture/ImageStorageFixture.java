package es.princip.getp.infra.storage.fixture;

import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Path;

import static es.princip.getp.infra.storage.fixture.StorageFixture.STORAGE_PATH;

public class ImageStorageFixture {

    public static final Path IMAGE_STORAGE_PATH =  STORAGE_PATH.resolve("images");

    public static MockMultipartFile imageMultiPartFile() {
        return new MockMultipartFile(
            "image",
            new byte[] {0x00, 0x01, 0x02, 0x03}
        );
    }
}
