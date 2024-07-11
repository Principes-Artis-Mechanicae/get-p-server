package es.princip.getp.domain.storage.application;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static es.princip.getp.domain.storage.fixture.ImageStorageFixture.STORAGE_PATH;
import static es.princip.getp.domain.storage.fixture.ImageStorageFixture.createImageMultiPartFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@Slf4j
class ImageStorageServiceTest {

    private final ImageStorageService imageStorageService = new ImageStorageService(STORAGE_PATH);

    @Nested
    @DisplayName("storeImage()는 주어진 경로에 사진을 저장하고, deleteImage()는 주어진 경로의 사진을 삭제한다.")
    class StoreImageAndDeleteImage {

        @Test
        void storeImageAndDeleteImage() throws IOException {
            Path path = Paths.get("");
            MultipartFile multipartFile = createImageMultiPartFile();

            Path savedImagePath = imageStorageService.storeImage(path, multipartFile);

            log.info("savedImagePath: {}", savedImagePath);

            assertThat(imageStorageService.getStoragePath().resolve(savedImagePath))
                .hasBinaryContent(multipartFile.getBytes());
            assertThatCode(() -> imageStorageService.deleteImage(savedImagePath)).doesNotThrowAnyException();
        }
    }
}