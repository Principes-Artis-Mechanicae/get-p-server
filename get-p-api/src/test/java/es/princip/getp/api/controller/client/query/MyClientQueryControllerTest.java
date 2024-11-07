package es.princip.getp.api.controller.client.query;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.application.client.dto.response.ClientResponse;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.client.port.out.ClientQuery;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.member.model.MemberType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.controller.client.query.description.ClientResponseDescription.clientResponseDescription;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static es.princip.getp.fixture.client.AddressFixture.address;
import static es.princip.getp.fixture.common.EmailFixture.EMAIL;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.member.PhoneNumberFixture.PHONE_NUMBER;
import static es.princip.getp.fixture.member.ProfileImageFixture.profileImage;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyClientQueryControllerTest extends ControllerTest {

    @Autowired private ClientQuery clientQuery;

    @Nested
    class 내_의뢰자_정보_조회 {

        private final MemberId memberId = new MemberId(1L);
        private final LocalDateTime now = LocalDateTime.now();
        private final ClientResponse response = new ClientResponse(
            1L,
            NICKNAME,
            PHONE_NUMBER,
            EMAIL,
            profileImage(memberId).getUrl(),
            address(),
            now,
            now
        );

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/client/me")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void 의뢰자는_자신의_의뢰자_정보를_조회할_수_있다() throws Exception {
            given(clientQuery.findClientBy(memberId)).willReturn(response);

            perform()
                .andExpect(status().isOk())
                .andDo(document("client/get-my-client",
                    ResourceSnippetParameters.builder()
                        .tag("의뢰자")
                        .description("의뢰자는 자신의 의뢰자 정보를 조회할 수 있다.")
                        .summary("의뢰자 정보 조회")
                        .responseSchema(Schema.schema("ClientResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    responseFields(clientResponseDescription())
                ))
                .andDo(print());
        }
    }
}