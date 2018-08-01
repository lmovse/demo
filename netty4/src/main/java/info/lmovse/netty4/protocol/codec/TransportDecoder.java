package info.lmovse.netty4.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class TransportDecoder extends LengthFieldBasedFrameDecoder {

    private ProtocolCodec codec;

    public TransportDecoder(final ProtocolCodec codec) {
        super(1024 * 1024, 12, 4);
        this.codec = codec;
    }

    @Override
    protected Object decode(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
        ByteBuf buf = (ByteBuf) super.decode(ctx, in);
        return codec.decode(buf);
    }
}
