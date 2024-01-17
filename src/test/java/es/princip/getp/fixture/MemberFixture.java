package es.princip.getp.fixture;

import java.util.ArrayList;
import java.util.List;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.entity.MemberType;

public class MemberFixture {
    public static String EMAIL = "getp@princip.es";
    public static String PASSWORD = "getp@princip.es";
    public static String ROLE_TYPE = "ROLE_PEOPLE";

    public static Member createMember() {
        return Member.builder()
                        .email(EMAIL)
                        .password(PASSWORD)
                        .memberType(MemberType.valueOf(ROLE_TYPE))
                    .build();
    }

    public static List<Member> createMemberList(int count) {
        List<Member> memberList = new ArrayList<Member>();
        for (int i = 0; i < count; i++) {
            Member member = Member.builder()
                                    .email(EMAIL+"."+Integer.toString(i))
                                    .password(PASSWORD)
                                    .memberType(MemberType.valueOf(ROLE_TYPE))
                                .build();
            memberList.add(member);
        }
        return memberList;
    }
}
