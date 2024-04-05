package es.princip.getp.domain.people.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum PeopleOrder {
    PEOPLE_ID, INTEREST_COUNT, CREATED_AT;

    @JsonCreator
    public static PeopleOrder parsing(String inputValue) {
        return Stream.of(PeopleOrder.values())
            .filter(peopleOrder -> peopleOrder.toString().equals(inputValue.toUpperCase()))
            .findFirst()
            .orElse(null);
    }
}
