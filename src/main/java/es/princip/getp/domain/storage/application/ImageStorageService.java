package es.princip.getp.domain.storage.application;

import es.princip.getp.domain.storage.exception.ImageErrorCode;
import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.util.ImageUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
@Getter
public class ImageStorageService {

    public ImageStorageService(@Value("${spring.storage.path}") String storagePath) {
        this.storagePath = Paths.get(storagePath).normalize().toAbsolutePath();
        this.imageStoragePath = Paths.get("images");
    }

    private final Path storagePath; // 절대 경로
    private final Path imageStoragePath; // 상대 경로

    public Path storeImage(Path path, MultipartFile image) {
        validateImage(image);
        String randomizedFilename = ImageUtil.generateRandomFilename(image.getOriginalFilename());
        Path destination = resolveDestinationPath(path, randomizedFilename);
        copyImageToDestination(image, destination);
        return storagePath.relativize(destination);
    }

    public void deleteImage(Path path) {
        try {
            Files.delete(this.storagePath.resolve(path));
        } catch (IOException exception) {
            throw new BusinessLogicException(ImageErrorCode.IMAGE_DELETE_FAILED);
        }
    }

    private void validateImage(MultipartFile image) {
        try {
            if (image.isEmpty()) {
                throw new BusinessLogicException(ImageErrorCode.INVALID_IMAGE_FILE);
            }
            if (!ImageUtil.isValidImage(image.getInputStream())) {
                throw new BusinessLogicException(ImageErrorCode.NOT_ALLOWED_EXTENSION);
            }
        } catch (IOException exception) {
            log.debug("image validation failed: {}", exception.getMessage());
            throw new BusinessLogicException(ImageErrorCode.IMAGE_SAVE_FAILED);
        }
    }

    private Path resolveDestinationPath(Path path, String filename) {
        return storagePath.resolve(imageStoragePath)
            .resolve(path)
            .resolve(filename);
    }

    private void copyImageToDestination(MultipartFile image, Path destination) {
        try (InputStream inputStream = image.getInputStream()) {
            makeDirectories(destination.getParent());
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new BusinessLogicException(ImageErrorCode.IMAGE_SAVE_FAILED);
        }
    }

    private void makeDirectories(Path path) {
        File directory = new File(path.toUri());
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new BusinessLogicException(ImageErrorCode.IMAGE_SAVE_FAILED);
            }
        }
    }
}