package es.princip.getp.infra.storage.presentation;

import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.storage.application.FileUploadService;
import es.princip.getp.infra.storage.presentation.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

import static es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("storage")
public class FileStorageController {

    private final FileUploadService fileUploadService;

    @PostMapping("/files")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<FileUploadResponse>> uploadFile(
        @RequestPart final MultipartFile file
    ) {
        final URI fileUri = fileUploadService.uploadFile(file);
        final FileUploadResponse response = new FileUploadResponse(fileUri);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }
}
