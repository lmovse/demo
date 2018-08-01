package info.lmovse.netty4.time.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        // allocate 4 capacity byteBuf
        ByteBuf buffer = ctx.alloc().buffer(4);
        // write value to buf
        buffer.writeInt((int) (System.currentTimeMillis() / 1000L));
        ChannelFuture channelFuture = ctx.writeAndFlush(buffer);
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
