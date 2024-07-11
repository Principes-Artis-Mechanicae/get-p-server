package es.princip.getp.domain.people.fixture;

import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.member.dto.request.UpdateMemberRequest;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleType;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;
import es.princip.getp.domain.people.dto.response.people.CardPeopleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.LongStream;

public class PeopleFixture {

    public static String NICKNAME = "knu12370";
    public static String EMAIL = "getp@princip.es";
    public static String PHONE_NUMBER = "01012345678";
    public static PeopleType PEOPLE_TYPE = PeopleType.INDIVIDUAL;
    public static String PROFILE_IMAGE_URI = "/images/1/profile/image.jpeg";
    private static String UPDATED = "updated_";

    public static CreatePeopleRequest createPeopleRequest() {
        return new CreatePeopleRequest(
            NICKNAME,
            EMAIL,
            PHONE_NUMBER,
            PEOPLE_TYPE
        );
    }

    public static UpdatePeopleRequest updatePeopleRequest() {
        return new UpdatePeopleRequest(
            UPDATED + NICKNAME,
            UPDATED + EMAIL,
            PHONE_NUMBER,
            PEOPLE_TYPE
        );
    }

    public static People createPeople(Member member) {
        CreatePeopleRequest request = createPeopleRequest();
        member.update(UpdateMemberRequest.from(request));
        return People.from(member, request);
    }

    public static List<People> createPeopleList(List<Member> memberList) {
        return memberList.stream().map(member -> People.builder()
            .email(EMAIL)
            .peopleType(PEOPLE_TYPE)
            .member(member)
            .build()).toList();
    }

    public static List<CardPeopleResponse> createCardPeopleResponses(int count) {
        return LongStream.range(1, count + 1)
            .mapToObj(i -> CardPeopleResponse.from(
                i,
                NICKNAME,
                PEOPLE_TYPE,
                PROFILE_IMAGE_URI,
                PeopleProfileFixture.createCardPeopleProfileResponse()))
            .toList();
    }

    public static Page<CardPeopleResponse> createCardPeopleResponsePage(Pageable pageable, int count) {
        return new PageImpl<>(createCardPeopleResponses(count), pageable, count);
    }
}
