package info.lmovse.netty.server.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import static org.jboss.netty.buffer.ChannelBuffers.buffer;

public class TimeServerHandler extends SimpleChannelHandler {

    @Override
    public void channelConnected(final ChannelHandlerContext ctx, final ChannelStateEvent e) {
        // setup a 4 bytes(32 bit) capacity channelBuffer
        ChannelBuffer buffer = buffer(4);
        int time = (int) (System.currentTimeMillis() / 1000L);
        buffer.writeInt(time);
        ChannelFuture channelFuture = e.getChannel().write(buffer);
        // when write completed, close channel
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final ExceptionEvent e) {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
