package es.princip.getp.domain.member.controller;

import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.domain.member.service.MemberService;
import es.princip.getp.domain.storage.exception.ImageErrorCode;
import es.princip.getp.global.controller.ErrorCodeController;
import es.princip.getp.global.mock.WithCustomMockUser;
import es.princip.getp.global.security.details.PrincipalDetails;
import es.princip.getp.global.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;

import static es.princip.getp.domain.client.fixture.ClientFixture.createClient;
import static es.princip.getp.domain.people.fixture.PeopleFixture.createPeople;
import static es.princip.getp.domain.storage.fixture.ImageStorageFixture.createImageMultiPartFile;
import static es.princip.getp.global.support.ErrorCodeFields.errorCodeFields;
import static es.princip.getp.global.support.FieldDescriptorHelper.getDescriptor;
import static es.princip.getp.global.support.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.global.support.PayloadDocumentationHelper.responseFields;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({MyMemberController.class, ErrorCodeController.class})
class MyMemberControllerTest extends AbstractControllerTest {

    @Autowired
    private MemberService memberService;

    @Nested
    @DisplayName("사용자는 프로필 사진을 올릴 수 있다.")
    class UploadProfileImage {

        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        public void uploadProfileImage() throws Exception {
            URI profileImageUri = URI.create("/images/1/profile-image/image.jpeg");
            given(memberService.updateProfileImage(any(), any())).willReturn(profileImageUri);

            mockMvc.perform(multipart("/member/me/profile-image")
                .file(createImageMultiPartFile())
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
            )
            .andExpect(status().isCreated())
            .andExpect(header().stringValues("Location", profileImageUri.toString()))
            .andDo(
                restDocs.document(
                    requestHeaders(
                        headerWithName("Authorization").description("Bearer ${ACCESS_TOKEN}")
                    ),
                    requestParts(
                        partWithName("image").description("프로필 이미지")
                    ),
                    responseHeaders(
                        headerWithName("Location").description("업로드된 프로필 사진의 URI")
                    )
                )
            )
            .andDo(print());
        }

        @Test
        void uploadProfileImageErrorCode() throws Exception {
            mockMvc.perform(get("/error-code/storage/images"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(errorCodeFields(ImageErrorCode.values())));
        }
    }

    @Nested
    @DisplayName("사용자는 내 회원 정보를 조회할 수 있다.")
    class GetMyMember {

        ResultActions perform() throws Exception {
            return mockMvc.perform(get("/member/me")
                    .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                ).andDo(print());
        }

        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void getMyMember_WhenMemberTypeIsPeople(PrincipalDetails principalDetails) throws Exception {
            createPeople(principalDetails.getMember());

            perform()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        requestHeaders(
                            authorizationHeaderDescriptor()
                        ),
                        responseFields(
                            getDescriptor("memberId", "회원 ID"),
                            getDescriptor("email", "이메일"),
                            getDescriptor("nickname", "닉네임"),
                            getDescriptor("profileImageUri", "프로필 사진 URI"),
                            getDescriptor("memberType", "회원 유형"),
                            getDescriptor("createdAt", "회원 가입일")
                                .attributes(key("format").value("yyyy-MM-dd'T'HH:mm:ss")),
                            getDescriptor("updatedAt", "회원 정보 수정일")
                                .attributes(key("format").value("yyyy-MM-dd'T'HH:mm:ss"))
                        )
                    )
                );
        }

        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void getMyMember_WhenMemberTypeIsClient(PrincipalDetails principalDetails) throws Exception {
            createClient(principalDetails.getMember());

            perform()
                .andExpect(status().isOk());
        }

        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void getMyMember_WhenPeopleIsNotRegistered() throws Exception {
            perform()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.nickname").isEmpty())
                .andExpect(jsonPath("$.data.profileImageUri").isEmpty());
        }

        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void getMyMember_WhenClientIsNotRegistered() throws Exception {
            perform()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.nickname").isEmpty())
                .andExpect(jsonPath("$.data.profileImageUri").isEmpty());
        }
    }
}