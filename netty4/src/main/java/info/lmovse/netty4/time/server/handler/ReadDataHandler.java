package info.lmovse.netty4.time.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ReadDataHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        try {
            while (buf.isReadable()) {
                System.out.println(((char) buf.readByte()));
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        cause.getCause().printStackTrace();
        ctx.channel().close();
    }
}
