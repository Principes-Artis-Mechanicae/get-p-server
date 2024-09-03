package es.princip.getp.domain.common;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class StubClockHolder implements ClockHolder {

    private final LocalDate now;

    public StubClockHolder(final LocalDate now) {
        this.now = now;
    }

    @Override
    public Clock getClock() {
        return Clock.fixed(
            now.atStartOfDay(ZoneOffset.UTC).toInstant(),
            ZoneId.systemDefault()
        );
    }
}
