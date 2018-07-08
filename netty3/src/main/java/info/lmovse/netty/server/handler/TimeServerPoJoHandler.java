package info.lmovse.netty.server.handler;

import info.lmovse.netty.pojo.UnixTime;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class TimeServerPoJoHandler extends SimpleChannelHandler {

    @Override
    public void channelConnected(final ChannelHandlerContext ctx, final ChannelStateEvent e) {
        UnixTime unixTime = new UnixTime((int) (System.currentTimeMillis() / 1000L));
        ChannelFuture channelFuture = e.getChannel().write(unixTime);
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final ExceptionEvent e) {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
