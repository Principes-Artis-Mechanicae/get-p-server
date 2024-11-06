package es.princip.getp.api.controller.people.query;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.application.people.dto.response.people.MyPeopleResponse;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.application.auth.service.PrincipalDetails;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.people.port.in.GetMyPeopleQuery;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.member.model.MemberType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.controller.people.query.description.MyPeopleResponseDescription.myPeopleResponseDescription;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static es.princip.getp.fixture.common.EmailFixture.EMAIL;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.member.PhoneNumberFixture.PHONE_NUMBER;
import static es.princip.getp.fixture.member.ProfileImageFixture.profileImage;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyPeopleQueryControllerTest extends ControllerTest {

    @Autowired private GetMyPeopleQuery getMyPeopleQuery;

    @Nested
    class 내_피플_정보_조회 {

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/people/me")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        public void 피플은_자신의_정보를_조회할_수_있다(PrincipalDetails principalDetails) throws Exception {
            final MemberId memberId = principalDetails.getMember().getId();
            MyPeopleResponse response = new MyPeopleResponse(
                1L,
                EMAIL,
                NICKNAME,
                PHONE_NUMBER,
                profileImage(memberId).getUrl(),
                0,
                0,
                LocalDateTime.now(),
                LocalDateTime.now()
            );
            given(getMyPeopleQuery.getBy(memberId)).willReturn(response);

            perform()
                .andExpect(status().isOk())
                .andDo(document("people/get-my-people",
                    ResourceSnippetParameters.builder()
                        .tag("피플")
                        .description("피플은 자신의 정보를 조회할 수 있다.")
                        .summary("내 피플 정보 조회")
                        .responseSchema(Schema.schema("MyPeopleResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    responseFields(myPeopleResponseDescription())
                ))
                .andDo(print());
        }
    }
}