package es.princip.getp.storage.application;

import es.princip.getp.storage.domain.FileLog;
import es.princip.getp.storage.domain.FileLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FileUploadLoggingService {

    private final FileLogRepository fileLogRepository;

    @Transactional
    public FileLog logFile(final String fileName) {
        final String converted = fileName.replace(" ", "_");
        final FileLog fileLog = new FileLog(converted);
        fileLogRepository.save(fileLog);
        return fileLog;
    }
}
