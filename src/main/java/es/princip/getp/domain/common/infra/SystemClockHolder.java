package es.princip.getp.domain.common.infra;

import es.princip.getp.domain.common.domain.ClockHolder;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class SystemClockHolder implements ClockHolder {

    @Override
    public Clock getClock() {
        return Clock.systemDefaultZone();
    }
}
