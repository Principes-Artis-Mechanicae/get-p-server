package es.princip.getp.api.controller.member.command;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.member.command.RegisterProfileImageCommand;
import es.princip.getp.application.member.port.in.ProfileImageUseCase;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.member.model.MemberType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static es.princip.getp.fixture.member.ProfileImageFixture.profileImage;
import static es.princip.getp.fixture.storage.MultipartFileFixture.imageMultiPartFile;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyMemberControllerTest extends ControllerTest {

    @Autowired
    private ProfileImageUseCase profileImageUseCase;

    @Nested
    class 프로필_사진_업로드 {

        private final MemberId memberId = new MemberId(1L);

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        public void 사용자는_프로필_사진을_올릴_수_있다() throws Exception {
            given(profileImageUseCase.registerProfileImage(any(RegisterProfileImageCommand.class)))
                .willReturn(profileImage(memberId).getUrl());

            mockMvc.perform(multipart("/member/me/profile-image")
                .file(imageMultiPartFile())
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"))
                .andExpect(status().isCreated())
                .andDo(document("member/upload-profile-image",
                    ResourceSnippetParameters.builder()
                        .tag("회원")
                        .description("사용자는 프로필 사진을 올릴 수 있다.")
                        .summary("프로필 사진 업로드")
                        .requestSchema(Schema.schema("UploadProfileImageRequest"))
                        .responseSchema(Schema.schema("UploadProfileImageResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    requestParts(partWithName("image").description("프로필 이미지")),
                    responseFields(
                        fieldWithPath("status").description("응답 상태"),
                        fieldWithPath("data.profileImageUri").description("업로드된 프로필 사진의 URI")
                    )
                ))
                .andDo(print());
        }
    }
}