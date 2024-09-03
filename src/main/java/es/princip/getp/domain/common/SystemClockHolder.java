package es.princip.getp.domain.common;

import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
class SystemClockHolder implements ClockHolder {

    @Override
    public Clock getClock() {
        return Clock.systemDefaultZone();
    }
}
