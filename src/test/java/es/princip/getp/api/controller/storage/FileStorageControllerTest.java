package es.princip.getp.api.controller.storage;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.application.storage.command.UploadFileCommand;
import es.princip.getp.application.storage.port.in.UploadFileUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.api.docs.PayloadDocumentationHelper.responseFields;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FileStorageControllerTest extends ControllerTest {

    @Autowired
    private UploadFileUseCase uploadFileUseCase;

    @Nested
    @DisplayName("파일 업로드")
    class UploadFile {

        final MockMultipartFile file = new MockMultipartFile("file", "dummy".getBytes());

        private ResultActions perform() throws Exception {
            return mockMvc.perform(multipart("/storage/files")
                .file(file)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        @WithCustomMockUser
        @DisplayName("사용자는 파일을 업로드할 수 있다.")
        void uploadFile() throws Exception {
            given(uploadFileUseCase.upload(any(UploadFileCommand.class)))
                .willReturn(URI.create("https://storage.principes.xyz/1/files/1/test.pdf"));

            perform()
                .andExpect(status().isCreated())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        requestParts(partWithName("file").description("업로드할 파일")),
                        responseFields(
                            getDescriptor("fileUri", "업로드된 파일 URI")
                        )
                    )
                )
                .andDo(print());
        }
    }
}