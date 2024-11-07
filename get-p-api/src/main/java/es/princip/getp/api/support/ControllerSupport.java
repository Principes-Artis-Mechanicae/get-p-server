package es.princip.getp.api.support;

import es.princip.getp.application.auth.service.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class ControllerSupport {

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public boolean isAuthenticated(final PrincipalDetails principalDetails) {
        return isAuthenticated() && principalDetails != null;
    }
}
