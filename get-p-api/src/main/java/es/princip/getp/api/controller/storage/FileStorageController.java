package es.princip.getp.api.controller.storage;

import es.princip.getp.application.auth.service.PrincipalDetails;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.application.storage.dto.command.UploadFileCommand;
import es.princip.getp.application.storage.dto.response.UploadFileResponse;
import es.princip.getp.application.storage.port.in.UploadFileUseCase;
import es.princip.getp.domain.member.model.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

import static es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("storage")
public class FileStorageController {

    private final UploadFileUseCase uploadFileUseCase;

    @PostMapping("/files")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<UploadFileResponse>> uploadFile(
        @AuthenticationPrincipal final PrincipalDetails principalDetails,
        @RequestPart final MultipartFile file
    ) {
        final MemberId memberId = principalDetails.getMember().getId();
        final UploadFileCommand command = new UploadFileCommand(memberId, file);
        final URI uri = uploadFileUseCase.upload(command);
        final UploadFileResponse response = new UploadFileResponse(uri);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }
}
