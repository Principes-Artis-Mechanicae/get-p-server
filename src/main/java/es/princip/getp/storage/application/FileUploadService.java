package es.princip.getp.storage.application;

import es.princip.getp.storage.domain.FileLog;
import es.princip.getp.storage.exception.FailedFileSaveException;
import es.princip.getp.storage.exception.NotSupportedExtensionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final FileStorage fileStorage;
    private final FileUploadLoggingService fileUploadLoggingService;

    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
        "application/pdf",
        "application/zip",
        "application/vnd.openxmlformats-officedocument.presentationml.presentation",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "application/x-hwp",
        "image/jpeg",
        "image/png"
    );

    public URI uploadFile(final MultipartFile file) {
        validateContentType(file);

        if (file.getOriginalFilename() == null) {
            throw new FailedFileSaveException();
        }

        final FileLog fileLog = fileUploadLoggingService.logFile(file.getOriginalFilename());
        final Path filePath = fileLog.getFilePath();

        try (final InputStream in = file.getInputStream()) {
            return fileStorage.storeFile(in, filePath);
        } catch (Exception exception) {
            throw new FailedFileSaveException();
        }
    }

    private void validateContentType(final MultipartFile file) {
        final String contentType = file.getContentType();

        if (!ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new NotSupportedExtensionException();
        }
    }
}
