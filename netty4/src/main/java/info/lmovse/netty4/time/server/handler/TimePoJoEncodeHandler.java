package info.lmovse.netty4.time.server.handler;

import info.lmovse.netty4.time.pojo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TimePoJoEncodeHandler extends MessageToByteEncoder<UnixTime> {

    @Override
    protected void encode(final ChannelHandlerContext ctx, final UnixTime msg, final ByteBuf out) {
        out.writeInt(msg.getValue());
    }
}
