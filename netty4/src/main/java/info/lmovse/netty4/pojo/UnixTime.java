package info.lmovse.netty4.pojo;

import java.util.Date;

public class UnixTime {
    private final int value;

    public UnixTime(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new Date(value * 1000L).toString();
    }
}
