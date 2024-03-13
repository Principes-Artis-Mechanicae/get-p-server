package es.princip.getp.domain.member.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum MemberType {
    ROLE_PEOPLE, ROLE_CLIENT, ROLE_MANAGER, ROLE_ADMIN;

    @JsonCreator
    public static MemberType parsing(String inputValue) {
        return Stream.of(MemberType.values())
            .filter(memberType -> memberType.toString().equals(inputValue.toUpperCase()))
            .findFirst()
            .orElse(null);
    }
}
