package es.princip.getp.api.controller.people.query;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.controller.people.query.description.MyPeopleResponseDescription;
import es.princip.getp.api.controller.people.query.dto.people.MyPeopleResponse;
import es.princip.getp.api.docs.PayloadDocumentationHelper;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.domain.people.query.dao.PeopleDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.domain.member.model.MemberType.ROLE_CLIENT;
import static es.princip.getp.domain.member.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.domain.member.fixture.EmailFixture.EMAIL;
import static es.princip.getp.domain.member.fixture.NicknameFixture.NICKNAME;
import static es.princip.getp.domain.member.fixture.PhoneNumberFixture.PHONE_NUMBER;
import static es.princip.getp.domain.member.fixture.ProfileImageFixture.profileImage;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyPeopleQueryControllerTest extends ControllerTest {

    @Autowired
    private PeopleDao peopleDao;

    @Nested
    @DisplayName("내 피플 정보 조회")
    class GetMyPeople {

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/people/me")
                    .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        @DisplayName("피플은 자신의 정보를 조회할 수 있다.")
        public void getMyPeople(PrincipalDetails principalDetails) throws Exception {
            Long memberId = principalDetails.getMember().getMemberId();
            MyPeopleResponse response = new MyPeopleResponse(
                1L,
                EMAIL,
                NICKNAME,
                PHONE_NUMBER,
                profileImage(1L).getUrl(),
                PeopleType.INDIVIDUAL,
                0,
                0,
                LocalDateTime.now(),
                LocalDateTime.now()
            );
            given(peopleDao.findByMemberId(memberId)).willReturn(response);

            perform()
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    PayloadDocumentationHelper.responseFields(MyPeopleResponseDescription.description())
                ))
                .andDo(print());
        }

        @Test
        @WithCustomMockUser(memberType = ROLE_CLIENT)
        public void getMyPeople_WhenMemberTypeIsClient_ShouldFail() throws Exception {
            expectForbidden(perform());
        }
    }
}