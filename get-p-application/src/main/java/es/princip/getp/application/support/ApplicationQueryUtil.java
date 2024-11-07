package es.princip.getp.application.support;

import es.princip.getp.domain.member.model.Member;

public class ApplicationQueryUtil {
    public static boolean isNotLogined(Member member) {
        if (member == null) {
            return true;
        }
        return false;
    }
}
