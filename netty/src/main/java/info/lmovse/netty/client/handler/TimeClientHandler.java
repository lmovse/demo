package info.lmovse.netty.client.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import java.util.Date;

public class TimeClientHandler extends SimpleChannelHandler {

    @Override
    public void messageReceived(final ChannelHandlerContext ctx, final MessageEvent e) {
        ChannelBuffer bf = (ChannelBuffer) e.getMessage();
        long time = bf.readInt() * 1000L;
        System.out.println(new Date(time));
        e.getChannel().close();
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final ExceptionEvent e) {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
