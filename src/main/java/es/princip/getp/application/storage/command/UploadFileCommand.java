package es.princip.getp.application.storage.command;

import es.princip.getp.domain.member.model.MemberId;
import org.springframework.web.multipart.MultipartFile;

public record UploadFileCommand(
    MemberId memberId,
    MultipartFile file
) {
}
