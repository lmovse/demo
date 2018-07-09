package info.lmovse.netty3.client.handler;

import info.lmovse.netty3.pojo.UnixTime;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class TimeClientPoJoHandler extends SimpleChannelHandler {

    @Override
    public void messageReceived(final ChannelHandlerContext ctx, final MessageEvent e) {
        UnixTime unixTime = (UnixTime) e.getMessage();
        System.out.println(unixTime.toString());
        e.getChannel().close();
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final ExceptionEvent e) {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
