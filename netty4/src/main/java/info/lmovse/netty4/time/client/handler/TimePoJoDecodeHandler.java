package info.lmovse.netty4.time.client.handler;

import info.lmovse.netty4.time.pojo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TimePoJoDecodeHandler extends ByteToMessageDecoder {
    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) {
        if (in.readableBytes() < 4) {
            return;
        }
        out.add(new UnixTime((int) in.readUnsignedInt()));
    }
}
