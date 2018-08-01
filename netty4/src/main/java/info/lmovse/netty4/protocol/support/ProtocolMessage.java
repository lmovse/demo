package info.lmovse.netty4.protocol.support;

public class ProtocolMessage {
    private ProtocolHeader header;
    private Object body;

    public ProtocolMessage(final ProtocolHeader header, final Object body) {
        this.header = header;
        this.body = body;
    }

    public ProtocolHeader getHeader() {
        return header;
    }

    public Object getBody() {
        return body;
    }
}
