package es.princip.getp.application.member.command;

import es.princip.getp.domain.member.model.MemberId;
import org.springframework.web.multipart.MultipartFile;

public record RegisterProfileImageCommand(
    MemberId memberId,
    MultipartFile image
) {
}
