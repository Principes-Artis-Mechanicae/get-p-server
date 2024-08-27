package es.princip.getp.api.controller.storage;

import es.princip.getp.api.controller.common.dto.ApiResponse;
import es.princip.getp.api.controller.storage.dto.FileUploadResponse;
import es.princip.getp.storage.application.FileUploadService;
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

import static es.princip.getp.api.controller.common.dto.ApiResponse.ApiSuccessResult;

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
