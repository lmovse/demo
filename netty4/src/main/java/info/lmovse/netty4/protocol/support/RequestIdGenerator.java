package info.lmovse.netty4.protocol.support;

import java.util.concurrent.atomic.AtomicLong;

public class RequestIdGenerator {

    private static final AtomicLong REQUEST_ID = new AtomicLong(0);

    public static long generateId() {
        return REQUEST_ID.getAndIncrement();
    }

}
