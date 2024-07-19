package es.princip.getp.infra.storage;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.storage.exception.ImageErrorCode;
import es.princip.getp.infra.util.ImageUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
@Slf4j
@Getter
public class ImageStorage {

    public ImageStorage(@Value("${spring.storage.path}") String storagePath) {
        this.storagePath = Paths.get(storagePath).normalize().toAbsolutePath();
        this.imageStoragePath = Paths.get("images");
    }

    private final Path storagePath; // 절대 경로
    private final Path imageStoragePath; // 상대 경로

    /**
     * 사진을 저장하고 저장된 사진의 URI를 반환합니다.
     *
     * @param destination 사진을 저장할 경로
     * @param imageStream 사진의 InputStream
     * @return imageStoragePath부터 시작하는 URI
     */
    public String storeImage(Path destination, InputStream imageStream) {
        validateImage(imageStream);
        copyImageToDestination(imageStream, resolvePath(destination));
        return "/" + storagePath.relativize(destination).toUri();
    }

    /**
     * 주어진 경로의 사진을 삭제합니다.
     *
     * @param destination 삭제할 사진 경로
     */
    public void deleteImage(Path destination) {
        try {
            if (destination.startsWith(getAbsoluteImageStoragePath())) {
                Files.delete(destination);
            } else {
                Files.delete(this.storagePath.resolve(destination));
            }
        } catch (IOException exception) {
            throw new BusinessLogicException(ImageErrorCode.IMAGE_DELETE_FAILED);
        }
    }

    /**
     * 이미지 스토리지의 절대 경로를 반환합니다.
     *
     * @return 이미지 스토리지의 절대 경로
     */
    private Path getAbsoluteImageStoragePath() {
        return storagePath.resolve(imageStoragePath);
    }

    /**
     * 이미지의 확장자를 검증합니다.
     *
     * @param imageStream 이미지의 InputStream
     */
    private void validateImage(InputStream imageStream) {
        if (!ImageUtil.isValidImage(imageStream)) {
            throw new BusinessLogicException(ImageErrorCode.NOT_ALLOWED_EXTENSION);
        }
    }

    /**
     * path의 절대 경로를 반환합니다.
     *
     * @param path path의 상대 경로
     * @return path의 절대 경로
     */
    private Path resolvePath(Path path) {
        return storagePath.resolve(imageStoragePath)
            .resolve(path);
    }

    /**
     * 이미지를 destination에 복사합니다.
     *
     * @param imageStream 이미지의 InputStream
     * @param destination 이미지를 저장할 경로
     */
    private void copyImageToDestination(InputStream imageStream, Path destination) {
        try {
            makeDirectories(destination.getParent());
            Files.copy(imageStream, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new BusinessLogicException(ImageErrorCode.IMAGE_SAVE_FAILED);
        }
    }

    /**
     * path에 디렉토리를 생성합니다.
     *
     * @param path 디렉토리를 생성할 경로
     */
    private void makeDirectories(Path path) {
        File directory = new File(path.toUri());
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new BusinessLogicException(ImageErrorCode.IMAGE_SAVE_FAILED);
            }
        }
    }
}