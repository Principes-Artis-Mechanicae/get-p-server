package es.princip.getp.domain.common.infrastructure;

import es.princip.getp.domain.common.service.ClockHolder;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
class SystemClockHolder implements ClockHolder {

    @Override
    public Clock getClock() {
        return Clock.systemDefaultZone();
    }
}
