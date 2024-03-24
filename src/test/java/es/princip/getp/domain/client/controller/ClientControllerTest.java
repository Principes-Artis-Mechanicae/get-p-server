package es.princip.getp.domain.client.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.princip.getp.domain.client.domain.entity.Client;
import es.princip.getp.domain.client.dto.request.CreateClientRequest;
import es.princip.getp.domain.client.service.ClientService;
import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.fixture.ClientFixture;
import es.princip.getp.global.config.SecurityConfig;
import es.princip.getp.global.config.SecurityTestConfig;
import es.princip.getp.global.mock.PrincipalDetailsParameterResolver;
import es.princip.getp.global.mock.WithCustomMockUser;
import es.princip.getp.global.security.details.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ClientController.class)
@Import({SecurityConfig.class, SecurityTestConfig.class})
@ActiveProfiles("test")
@RequiredArgsConstructor
@Slf4j
@ExtendWith(PrincipalDetailsParameterResolver.class)
@DisplayName("의뢰자는")
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @Nested
    @DisplayName("의뢰자 정보 등록")
    class Create {

        @Test
        @DisplayName("의뢰자 정보를 등록할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void create(PrincipalDetails principalDetails) throws Exception {
            //given
            Member member = principalDetails.getMember();
            CreateClientRequest request = ClientFixture.createClientRequest();
            Client client = Client.from(member, request);
            given(clientService.create(any(), any())).willReturn(client);

            //when and then
            mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.clientId")
                    .value(client.getClientId()))
                .andExpect(jsonPath("$.data.nickname")
                    .value(client.getNickname()))
                .andExpect(jsonPath("$.data.email")
                    .value(client.getEmail()))
                .andExpect(jsonPath("$.data.phoneNumber")
                    .value(client.getPhoneNumber()))
                .andExpect(jsonPath("$.data.profileImageUri")
                    .value(client.getProfileImageUri()))
                .andExpect(jsonPath("$.data.address.zipcode")
                    .value(client.getAddress().getZipcode()))
                .andExpect(jsonPath("$.data.address.street")
                    .value(client.getAddress().getStreet()))
                .andExpect(jsonPath("$.data.address.detail")
                    .value(client.getAddress().getDetail()))
                .andExpect(jsonPath("$.data.bankAccount.bank")
                    .value(client.getBankAccount().getBank()))
                .andExpect(jsonPath("$.data.bankAccount.accountNumber")
                    .value(client.getBankAccount().getAccountNumber()))
                .andExpect(jsonPath("$.data.bankAccount.accountHolder")
                    .value(client.getBankAccount().getAccountHolder()));
        }
    }
}