package es.princip.getp.infra.storage.infra;

import es.princip.getp.infra.storage.application.FileStorage;
import es.princip.getp.infra.storage.exception.FailedFileSaveException;
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
public class LocalFileStorage implements FileStorage {

    public LocalFileStorage(
        @Value("${spring.storage.base-uri}") String baseUri,
        @Value("${spring.storage.local.path}") String storagePath
    ) {
        this.baseUri = baseUri;
        this.storagePath = Paths.get(storagePath).normalize().toAbsolutePath();
        this.fileStoragePath = Paths.get("files");
    }

    private final String baseUri;
    private final Path storagePath; // 절대 경로
    private final Path fileStoragePath; // 상대 경로

    @Override
    public URI storeFile(final InputStream in, final Path destination) throws IOException {
        final Path resolvedPath = resolvePath(destination);
        makeDirectories(resolvedPath.getParent());
        Files.copy(in, resolvedPath, StandardCopyOption.REPLACE_EXISTING);
        return createFileUri(resolvedPath);
    }

    private Path resolvePath(final Path path) {
        return storagePath.resolve(fileStoragePath).resolve(path);
    }

    private void makeDirectories(final Path path) {
        final File directory = new File(path.toUri());
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new FailedFileSaveException();
            }
        }
    }

    private URI createFileUri(final Path path) {
        final Path relativePath = storagePath.relativize(path);
        final String fileUri = relativePath.toString().replace("\\", "/");
        return URI.create(baseUri + fileUri);
    }
}