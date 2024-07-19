package es.princip.getp.domain.people.fixture;

import es.princip.getp.domain.people.command.domain.Education;

public class EducationFixture {

    public static final String SCHOOL = "경북대학교";
    public static final String MAJOR = "컴퓨터공학";

    public static Education education() {
        return Education.of(SCHOOL, MAJOR);
    }
}
