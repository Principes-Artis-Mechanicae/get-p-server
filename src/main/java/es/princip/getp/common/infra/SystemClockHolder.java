package es.princip.getp.common.infra;

import es.princip.getp.common.domain.ClockHolder;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class SystemClockHolder implements ClockHolder {

    @Override
    public Clock getClock() {
        return Clock.systemDefaultZone();
    }
}
