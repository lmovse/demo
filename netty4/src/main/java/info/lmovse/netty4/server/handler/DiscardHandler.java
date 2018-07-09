package info.lmovse.netty4.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DiscardHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        ((ByteBuf) msg).release();
    }
}
