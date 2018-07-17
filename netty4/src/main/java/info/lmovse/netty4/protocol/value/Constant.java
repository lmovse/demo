package info.lmovse.netty4.protocol.value;

public final class Constant {

    public static final byte FLAG_REQUEST = (byte) 0x80;
    public static final byte FLAG_TWOWAY = (byte) 0x40;
    public static final byte FLAG_EVENT = (byte) 0x20;
    public static final short MAGIC = (short) 0xbabe;

    private Constant() {
    }
}
