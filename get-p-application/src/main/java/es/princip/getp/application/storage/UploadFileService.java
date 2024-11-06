package es.princip.getp.application.storage;

import es.princip.getp.application.storage.dto.command.UploadFileCommand;
import es.princip.getp.application.storage.exception.NotSupportedExtensionException;
import es.princip.getp.application.storage.port.in.UploadFileUseCase;
import es.princip.getp.application.storage.port.out.LogFilePort;
import es.princip.getp.application.storage.port.out.StoreFilePort;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.application.storage.exception.FailedFileSaveException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.file.Path;
import java.util.Set;

@Service
@RequiredArgsConstructor
class UploadFileService implements UploadFileUseCase {

    private final StoreFilePort storeFilePort;
    private final LogFilePort logFilePort;

    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
        "application/pdf",
        "application/zip",
        "application/vnd.openxmlformats-officedocument.presentationml.presentation",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "application/x-hwp",
        "image/jpeg",
        "image/png"
    );

    private void validateFilename(final MultipartFile file) {
        if (file.getOriginalFilename() == null) {
            throw new FailedFileSaveException();
        }
    }

    private void validateContentType(final MultipartFile file) {
        final String contentType = file.getContentType();
        if (!ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new NotSupportedExtensionException();
        }
    }

    private void validateFile(final MultipartFile file) {
        validateFilename(file);
        validateContentType(file);
    }

    @Override
    @Transactional
    public URI upload(final UploadFileCommand command) {
        final MemberId memberId = command.memberId();
        final MultipartFile file = command.file();
        validateFile(file);
        final FileLog fileLog = logFilePort.log(FileLog.of(memberId, file));
        final Path path = fileLog.getPath();
        return storeFilePort.store(file, path);
    }
}
