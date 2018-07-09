package info.lmovse.netty4.client.handler;

import info.lmovse.netty4.pojo.UnixTime;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientPoJoHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        UnixTime unixTime = (UnixTime) msg;
        System.out.println(unixTime.toString());
        ctx.close();
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
