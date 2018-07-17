package info.lmovse.netty4.time.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import static java.util.Objects.nonNull;

public class FileServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final String msg) throws Exception {
        RandomAccessFile file = null;
        long length = -1;
        try {
            file = new RandomAccessFile(msg, "r");
            length = file.length();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ctx.writeAndFlush("wrong: read file error!");
            return;
        } finally {
            if (length < 0 && nonNull(file)) {
                // if content is not valid, close fd
                file.close();
            }
        }
        ctx.write(new DefaultFileRegion(file.getChannel(), 0, length));
        ctx.writeAndFlush("\n");
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            ctx.writeAndFlush("wrong: server error!");
        }
    }
}
