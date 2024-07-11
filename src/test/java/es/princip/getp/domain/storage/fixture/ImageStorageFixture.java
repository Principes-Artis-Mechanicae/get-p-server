package es.princip.getp.domain.storage.fixture;

import es.princip.getp.infra.util.PathUtil;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageStorageFixture {
    public static final String STORAGE_PATH = "test/storage";

    private static final Path IMAGE_STORAGE_PATH = PathUtil.getRootPath()
        .resolve(STORAGE_PATH)
        .resolve("images");

    private static final Path IMAGE_PATH = IMAGE_STORAGE_PATH.resolve("image.jpeg");

    public static MockMultipartFile createImageMultiPartFile() throws IOException {
        return new MockMultipartFile(
            "image",
            IMAGE_PATH.toString(),
            MediaType.IMAGE_JPEG_VALUE,
            Files.readAllBytes(IMAGE_PATH)
        );
    }
}
