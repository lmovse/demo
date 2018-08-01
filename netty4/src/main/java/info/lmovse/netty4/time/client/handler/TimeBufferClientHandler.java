package info.lmovse.netty4.time.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class TimeBufferClientHandler extends ChannelInboundHandlerAdapter {

    private ByteBuf buf;

    @Override
    public void handlerAdded(final ChannelHandlerContext ctx) {
        buf = ctx.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved(final ChannelHandlerContext ctx) {
        buf.release();
        buf = null;
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        ByteBuf message = (ByteBuf) msg;
        buf.writeBytes(message);
        message.release();
        if (buf.readableBytes() >= 4) {
            System.out.println(new Date(buf.readUnsignedInt() * 1000L));
            ctx.close();
            // wrong! cannot release cause of will continue receive data at next
//            buf.release();
        }
    }
}
