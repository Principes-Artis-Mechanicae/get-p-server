package es.princip.getp.fixture;

import es.princip.getp.domain.client.dto.request.CreateClientRequest;
import es.princip.getp.domain.client.dto.request.UpdateClientRequest;
import es.princip.getp.domain.client.entity.Client;
import es.princip.getp.domain.member.entity.Member;

public class ClientFixture {
    public static String NAME = "겟피";
    public static String EMAIL = "getp@princip.es";
    public static String PHONE_NUMBER = "010-1234-5678";
    public static String PROFILE_IMAGE_URI = "https://he.princip.es/img.jpg";
    public static String ADDRESS = "대구광역시 북구";
    public static String ACCOUNT_NUMBER = "3332-112-12-12";

    public static CreateClientRequest createClientRequest() {
        return new CreateClientRequest(NAME, EMAIL, PHONE_NUMBER, NAME, EMAIL, ACCOUNT_NUMBER);
    }

    public static UpdateClientRequest updateClientRequest() {
        return new UpdateClientRequest(NAME+"verUpdate", EMAIL, PHONE_NUMBER, NAME, EMAIL, ACCOUNT_NUMBER);
    }

    public static Client createClientByMember(Member member) {
        return Client.builder()
                        .nickname(NAME)
                        .email(EMAIL)
                        .phoneNumber(PHONE_NUMBER)
                        .profileImageUri(PROFILE_IMAGE_URI)
                        .address(ADDRESS)
                        .accountNumber(ACCOUNT_NUMBER)
                        .member(member)
                    .build();
    }
}