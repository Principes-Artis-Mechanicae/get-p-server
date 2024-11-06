package es.princip.getp.api.controller.like.command;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.like.people.port.in.LikePeopleUseCase;
import es.princip.getp.application.like.people.port.in.UnlikePeopleUseCase;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.people.model.PeopleId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PeopleLikeControllerTest extends ControllerTest {

    @Autowired private LikePeopleUseCase likePeopleUseCase;
    @Autowired private UnlikePeopleUseCase unlikePeopleUseCase;

    @Nested
    class 피플_좋아요 {

        private final PeopleId peopleId = new PeopleId(1L);

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void 의뢰자는_피플에게_좋아요를_누를_수_있다() throws Exception {
            willDoNothing().given(likePeopleUseCase).like(any(), eq(peopleId));

            mockMvc.perform(post("/people/{peopleId}/likes", peopleId.getValue())
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"))
                .andExpect(status().isCreated())
                .andDo(document("like/like-people",
                    ResourceSnippetParameters.builder()
                        .tag("좋아요")
                        .description("의뢰자는 피플에게 좋아요를 누를 수 있다.")
                        .summary("피플 좋아요")
                        .responseSchema(Schema.schema("StatusResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    responseFields(statusField())
                ))
                .andDo(print());
        }
    }

    @Nested
    class 피플_좋아요_취소 {

        private final PeopleId peopleId = new PeopleId(1L);

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void 의뢰자는_피플에게_눌렀던_좋아요를_취소를_할_수_있다() throws Exception {
            willDoNothing().given(unlikePeopleUseCase).unlike(any(), eq(peopleId));

            mockMvc.perform(delete("/people/{peopleId}/likes", peopleId.getValue())
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"))
                .andExpect(status().isNoContent())
                .andDo(document("like/unlike-people",
                    ResourceSnippetParameters.builder()
                        .tag("좋아요")
                        .description("의뢰자는 피플에게 눌렀던 좋아요를 취소를 할 수 있다.")
                        .summary("피플 좋아요 취소")
                        .responseSchema(Schema.schema("StatusResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    responseFields(statusField())
                ))
                .andDo(print());
        }
    }
}