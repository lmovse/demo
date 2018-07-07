package info.lmovse.netty.server.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class ReadDataServerHandler extends SimpleChannelHandler {

    @Override
    public void messageReceived(final ChannelHandlerContext ctx, final MessageEvent e) {
        ChannelBuffer bf = (ChannelBuffer) e.getMessage();
        while (bf.readable()) {
            System.out.println((char) bf.readByte());
            System.out.flush();
        }
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final ExceptionEvent e) {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
