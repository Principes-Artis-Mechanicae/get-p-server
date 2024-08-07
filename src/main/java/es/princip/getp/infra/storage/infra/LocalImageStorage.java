package es.princip.getp.infra.storage.infra;

import es.princip.getp.infra.storage.application.ImageStorage;
import es.princip.getp.infra.storage.exception.FailedImageSaveException;
import es.princip.getp.infra.util.ImageUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
@Slf4j
@Getter
public class LocalImageStorage implements ImageStorage {

    public LocalImageStorage(
        @Value("${server.servlet.context-path}") String contextPath,
        @Value("${spring.storage.base-uri}") String baseUri,
        @Value("${spring.storage.local.path}") String storagePath
    ) {
        this.contextPath = contextPath;
        this.baseUri = baseUri;
        this.storagePath = Paths.get(storagePath).normalize().toAbsolutePath();
        this.imageStoragePath = Paths.get("images");
    }

    private final String contextPath;
    private final String baseUri;
    private final Path storagePath; // 절대 경로
    private final Path imageStoragePath; // 상대 경로

    /**
     * 사진을 저장하고 저장된 사진의 URI를 반환합니다.
     *
     * @param destination 사진을 저장할 경로
     * @param imageStream 사진의 InputStream
     * @return imageStoragePath부터 시작하는 URI
     */
    @Override
    public URI storeImage(Path destination, InputStream imageStream) throws IOException {
        validateImage(imageStream);
        final Path resolvedPath = resolvePath(destination);
        makeDirectories(resolvedPath.getParent());
        Files.copy(imageStream, resolvedPath, StandardCopyOption.REPLACE_EXISTING);
        return createFileUri(resolvedPath);
    }

    /**
     * 주어진 경로의 사진을 삭제합니다.
     *
     * @param destination 삭제할 사진 경로
     */
    @Override
    public void deleteImage(URI destination) {
        final Path path = Paths.get(destination.getPath().replace(contextPath, ""));
        try {
            if (path.startsWith(getAbsoluteImageStoragePath())) {
                Files.delete(path);
            } else {
                Files.delete(this.storagePath.resolve(path));
            }
        } catch (IOException exception) {
            throw new FailedImageSaveException();
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
            throw new FailedImageSaveException();
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
     * path에 디렉토리를 생성합니다.
     *
     * @param path 디렉토리를 생성할 경로
     */
    private void makeDirectories(Path path) {
        File directory = new File(path.toUri());
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new FailedImageSaveException();
            }
        }
    }

    private URI createFileUri(final Path path) {
        final Path relativePath = storagePath.relativize(path);
        final String fileUri = relativePath.toString().replace("\\", "/");
        return URI.create(baseUri + fileUri);
    }
}