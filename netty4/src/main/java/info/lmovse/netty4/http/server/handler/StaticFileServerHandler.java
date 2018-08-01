package info.lmovse.netty4.http.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.RandomAccessFile;

import static io.netty.buffer.Unpooled.wrappedBuffer;
import static java.io.File.separator;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class StaticFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final int MAX_CHUNK_LENGTH = 8192;

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final FullHttpRequest msg) {
        String uri = msg.getUri();
        if (uri.contains("favicon.ico")) {
            return;
        }
        String[] pathAry = uri.substring(1, uri.length()).split("/");
        String filePath = pathAry.length == 1 ? pathAry[0] : String.join(separator, pathAry);
        File fileResolver = new File(filePath.isEmpty() ? System.getProperty("user.dir") : filePath);
        HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        if (fileResolver.exists() && fileResolver.isDirectory()) {
            StringBuilder sb = new StringBuilder("<ul>");
            File[] files = fileResolver.listFiles();
            if (files != null && files.length > 0) {
                for (final File file : files) {
                    sb.append("<li><a href=\"")
                            .append(filePath.isEmpty() ? file.getName() : uri.concat(separator).concat(file.getName()))
                            .append("\">")
                            .append(file.getName())
                            .append("</a></li>");
                }
            }
            sb.append("</ul>");
            ByteBuf buf = wrappedBuffer(sb.toString().getBytes(CharsetUtil.UTF_8));
            ((DefaultFullHttpResponse) response).content().writeBytes(buf);
            buf.release();
            response.headers().add("content-type", "text/html;charset=UTF-8");
            ChannelFuture channelFuture = ctx.writeAndFlush(response);
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        } else if (fileResolver.exists() && fileResolver.isFile()) {
            RandomAccessFile file = null;
            ChunkedFile chunkedFile = null;
            try {
                file = new RandomAccessFile(filePath, "r");
                // big content download, avoid outOfMemoryError
                chunkedFile = new ChunkedFile(file, 0, file.length(), MAX_CHUNK_LENGTH);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            } finally {
                if (isNull(file)) {
                    response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
                    ChannelFuture channelFuture = ctx.writeAndFlush(response);
                    channelFuture.addListener(ChannelFutureListener.CLOSE);
                } else {
                    response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                    ctx.write(response);
                    ChannelFuture channelFuture = ctx.writeAndFlush(chunkedFile);
                    channelFuture.addListener(ChannelFutureListener.CLOSE);
                }
            }
        }
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        cause.printStackTrace();
        if (nonNull(ctx.channel())) {
            ctx.channel().close();
        }
    }
}
