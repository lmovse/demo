package info.lmovse.netty4.time.server.handler;

import info.lmovse.netty4.time.pojo.UnixTime;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerPoJoHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        UnixTime unixTime = new UnixTime((int) (System.currentTimeMillis() / 1000L));
        ChannelFuture channelFuture = ctx.writeAndFlush(unixTime);
        // when operate complete, close channel
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
