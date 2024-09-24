package es.princip.getp.application.people.command;

import es.princip.getp.util.StringUtil;

public enum PeopleSearchOrder {
    PEOPLE_ID, LIKES_COUNT, CREATED_AT;

    public static PeopleSearchOrder get(final String value) {
        return PeopleSearchOrder.valueOf(StringUtil.camelToSnake(value).toUpperCase());
    }
}
