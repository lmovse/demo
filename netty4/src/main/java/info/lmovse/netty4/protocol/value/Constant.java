package info.lmovse.netty4.protocol.value;

public final class Constant {

    // flag
    public static final byte FLAG_REQUEST = (byte) 0x80;
    public static final byte FLAG_TWOWAY = (byte) 0x40;
    public static final byte FLAG_EVENT = (byte) 0x20;
    public static final short MAGIC = (short) 0xbabe;

    // response status
    public static final byte OK = 20;
    public static final byte CLIENT_TIMEOUT = 30;
    public static final byte SERVER_TIMEOUT = 31;
    public static final byte BAD_REQUEST = 40;
    public static final byte BAD_RESPONSE = 50;
    public static final byte SERVICE_NOT_FOUND = 60;
    public static final byte SERVICE_ERROR = 70;
    public static final byte SERVER_ERROR = 80;
    public static final byte CLIENT_ERROR = 90;
    public static final byte SERVER_THREADPOOL_EXHAUSTED_ERROR = 100;

    private Constant() {
    }
}
