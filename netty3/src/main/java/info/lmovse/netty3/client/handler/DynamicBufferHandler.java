package info.lmovse.netty3.client.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import java.util.Date;

/**
 * <p>First solution to dealing with Stream-based Transport</p>
 * A dynamic buffer is a ChannelBuffer which increases its capacity on demand.
 * It's very useful when you don't know the length of the message.
 * First, all received data should be cumulated into buf.
 * And then, the handler must check if buf has enough data, 4 bytes in this example, and proceed to the actual business logic.
 * Otherwise, Netty will call the messageReceived method again when more data arrives, and eventually all 4 bytes will be cumulated.
 */
public class DynamicBufferHandler extends SimpleChannelHandler {

    private final ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    @Override
    public void messageReceived(final ChannelHandlerContext ctx, final MessageEvent e) {
        ChannelBuffer bf = (ChannelBuffer) e.getMessage();
        buffer.writeBytes(bf);
        if (buffer.readableBytes() >= 4) {
            System.out.println(new Date(buffer.readInt() * 1000L));
            e.getChannel().close();
        }
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final ExceptionEvent e) {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
