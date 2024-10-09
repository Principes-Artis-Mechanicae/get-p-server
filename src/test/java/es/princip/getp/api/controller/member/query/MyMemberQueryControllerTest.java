package es.princip.getp.api.controller.member.query;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.domain.member.model.MemberType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyMemberQueryControllerTest extends ControllerTest {

    @Nested
    class 내_회원_정보_조회 {

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/member/me")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        void 사용자는_자신의_회원_정보를_조회할_수_있다() throws Exception {

            perform()
                .andExpect(status().isOk())
                .andDo(document("member/get-my-member",
                    ResourceSnippetParameters.builder()
                        .tag("회원")
                        .description("사용자는 자신의 회원 정보를 조회할 수 있다.")
                        .summary("내 회원 정보 조회")
                        .responseSchema(Schema.schema("MemberResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    responseFields(
                        fieldWithPath("status").description("응답 상태"),
                        fieldWithPath("data.memberId").description("회원 ID"),
                        fieldWithPath("data.email").description("이메일"),
                        fieldWithPath("data.nickname").description("닉네임"),
                        fieldWithPath("data.profileImageUri").description("프로필 사진 URI"),
                        fieldWithPath("data.memberType").description("회원 유형"),
                        fieldWithPath("data.createdAt").description("회원 가입일")
                            .attributes(key("format").value("yyyy-MM-dd'T'HH:mm:ss")),
                        fieldWithPath("data.updatedAt").description("회원 정보 수정일")
                            .attributes(key("format").value("yyyy-MM-dd'T'HH:mm:ss"))
                    )
                ))
                .andDo(print());
        }
    }
}