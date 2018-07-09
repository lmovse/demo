package info.lmovse.netty3.server.handler;

import info.lmovse.netty3.pojo.UnixTime;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class TimePoJoEncodeHandler extends SimpleChannelHandler {

    @Override
    public void writeRequested(final ChannelHandlerContext ctx, final MessageEvent e) {
        UnixTime message = (UnixTime) e.getMessage();
        ChannelBuffer buffer = ChannelBuffers.buffer(4);
        buffer.writeInt(message.getValue());
        Channels.write(ctx, e.getFuture(), buffer);
    }
}
