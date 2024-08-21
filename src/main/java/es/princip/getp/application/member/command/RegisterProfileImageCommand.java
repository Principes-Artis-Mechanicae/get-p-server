package es.princip.getp.application.member.command;

import org.springframework.web.multipart.MultipartFile;

public record RegisterProfileImageCommand(
    Long memberId,
    MultipartFile image
) {
}
