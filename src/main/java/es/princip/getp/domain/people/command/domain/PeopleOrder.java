package es.princip.getp.domain.people.command.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import es.princip.getp.common.util.StringUtil;

import java.util.stream.Stream;

public enum PeopleOrder {
    PEOPLE_ID, INTEREST_COUNT, CREATED_AT;

    @JsonCreator
    public static PeopleOrder parsing(String inputValue) {
        return Stream.of(PeopleOrder.values())
            .filter(peopleOrder -> peopleOrder.toString().equals(StringUtil.camelToSnake(inputValue).toUpperCase()))
            .findFirst()
            .orElse(null);
    }

    public static PeopleOrder get(String value) {
        return PeopleOrder.valueOf(StringUtil.camelToSnake(value).toUpperCase());
    }
}
