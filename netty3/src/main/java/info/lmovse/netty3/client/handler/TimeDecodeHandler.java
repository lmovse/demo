package info.lmovse.netty3.client.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * FrameDecoder is an implementation of ChannelHandler which makes it easy to which deals with the fragmentation issue.
 * FrameDecoder calls decode method with an internally maintained cumulative buffer whenever new data is received.
 * If null is returned, it means there's not enough data yet. FrameDecoder will call again when there is a sufficient amount of data.
 * If non-null is returned, it means the decode method has decoded a message successfully. FrameDecoder will discard the read part of its internal cumulative buffer.
 * Please remember that you don't need to decode multiple messages. FrameDecoder will keep calling the decoder method until it returns null.
 */
public class TimeDecodeHandler extends FrameDecoder {

    @Override
    protected Object decode(final ChannelHandlerContext ctx, final Channel channel, final ChannelBuffer buffer) throws Exception {
        if (buffer.readableBytes() < 4) {
            return null;
        }
        return buffer.readBytes(4);
    }
}
