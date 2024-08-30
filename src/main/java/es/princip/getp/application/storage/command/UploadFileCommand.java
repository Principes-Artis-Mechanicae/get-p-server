package es.princip.getp.application.storage.command;

import org.springframework.web.multipart.MultipartFile;

public record UploadFileCommand(
    Long memberId,
    MultipartFile file
) {
}
