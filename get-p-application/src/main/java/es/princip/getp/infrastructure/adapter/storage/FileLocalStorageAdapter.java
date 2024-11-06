package es.princip.getp.infrastructure.adapter.storage;

import es.princip.getp.application.storage.port.out.DeleteFilePort;
import es.princip.getp.application.storage.port.out.StoreFilePort;
import es.princip.getp.infrastructure.adapter.storage.exception.FailedFileDeleteException;
import es.princip.getp.infrastructure.adapter.storage.exception.FailedFileSaveException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileLocalStorageAdapter implements StoreFilePort, DeleteFilePort {

    public FileLocalStorageAdapter(
        @Value("${spring.storage.base-uri}") String baseUri,
        @Value("${spring.storage.local.path}") String storagePath
    ) {
        this.baseUri = baseUri;
        this.storagePath = Paths.get(storagePath).normalize().toAbsolutePath();
    }

    private final String baseUri;
    private final Path storagePath;

    @Override
    public URI store(final MultipartFile file, final Path destination) {
        final Path resolvedPath = resolvePath(destination);
        try (final InputStream in = file.getInputStream()){
            Files.createDirectories(resolvedPath.getParent());
            Files.copy(in, resolvedPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception exception) {
            throw new FailedFileSaveException();
        }
        return createURI(resolvedPath);
    }

    private Path resolvePath(final Path path) {
        return storagePath.resolve(path);
    }

    private URI createURI(final Path path) {
        final Path relativePath = storagePath.relativize(path);
        final String uri = relativePath.toString().replace("\\", "/");
        return URI.create(baseUri + uri);
    }

    @Override
    public void delete(final URI destination) {
        Path path = Paths.get(destination.getPath().replaceFirst("/", ""));
        if (!path.startsWith(storagePath)) {
            path = storagePath.resolve(path);
        }
        try {
            Files.delete(path);
        } catch (Exception exception) {
            throw new FailedFileDeleteException();
        }
    }
}