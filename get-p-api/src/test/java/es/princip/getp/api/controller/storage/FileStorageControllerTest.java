package es.princip.getp.api.controller.storage;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.storage.dto.command.UploadFileCommand;
import es.princip.getp.application.storage.port.in.UploadFileUseCase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FileStorageControllerTest extends ControllerTest {

    @Autowired
    private UploadFileUseCase uploadFileUseCase;

    @Nested
    class 파일_업로드 {

        final MockMultipartFile file = new MockMultipartFile("file", "dummy".getBytes());

        private ResultActions perform() throws Exception {
            return mockMvc.perform(multipart("/storage/files")
                .file(file)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        @WithCustomMockUser
        void 사용자는_파일을_업로드할_수_있다() throws Exception {
            given(uploadFileUseCase.upload(any(UploadFileCommand.class)))
                .willReturn(URI.create("https://storage.principes.xyz/1/files/1/test.pdf"));

            perform()
                .andExpect(status().isCreated())
                .andDo(document("storage/upload-file",
                    ResourceSnippetParameters.builder()
                        .tag("스토리지")
                        .description("사용자는 파일을 업로드할 수 있다.")
                        .summary("파일 업로드")
                        .requestSchema(Schema.schema("UploadFileRequest"))
                        .responseSchema(Schema.schema("UploadFileResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    requestParts(partWithName("file").description("업로드할 파일")),
                    responseFields(
                        fieldWithPath("status").description("응답 상태"),
                        fieldWithPath("data.fileUri").description("업로드된 파일 URI")
                    )
                ))
                .andDo(print());
        }
    }
}