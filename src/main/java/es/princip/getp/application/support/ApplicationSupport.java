package es.princip.getp.application.support;

import es.princip.getp.domain.member.model.Member;

public abstract class ApplicationSupport {
    public boolean isNotLogined(Member member) {
        if (member == null) {
            return true;
        }
        return false;
    }
}
