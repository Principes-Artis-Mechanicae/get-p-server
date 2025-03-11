package es.princip.getp.batch;

import java.util.concurrent.atomic.AtomicLong;

public class UniqueLongGenerator {
    private static final AtomicLong counter = new AtomicLong();

    public static long generateUniqueLong() {
        return counter.incrementAndGet();
    }
}