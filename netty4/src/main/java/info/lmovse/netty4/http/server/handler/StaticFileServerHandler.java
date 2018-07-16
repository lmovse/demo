package info.lmovse.netty4.http.server.handler;

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

import java.io.File;
import java.io.RandomAccessFile;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public class StaticFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final FullHttpRequest msg) {
        String uri = msg.getUri();
        if (uri.contains("favicon.ico")) {
            return;
        }
        String[] pathAry = uri.substring(1, uri.length()).split("/");
        String filePath = pathAry.length == 1 ? pathAry[0] : String.join(File.separator, pathAry);
        File fileResolver = new File(filePath.isEmpty() ? System.getProperty("user.dir") : filePath);
        HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        if (fileResolver.isDirectory()) {
            StringBuilder sb = new StringBuilder("<ul>");
            for (final File file : requireNonNull(fileResolver.listFiles())) {
                sb.append("<li><a href=\"")
                        .append(filePath.isEmpty() ? file.getName() : uri.concat(File.separator).concat(file.getName()))
                        .append("\">")
                        .append(file.getName())
                        .append("</a></li>");
            }
            sb.append("</ul>");
            ((DefaultFullHttpResponse) response).content().writeBytes(sb.toString().getBytes());
            response.headers().add("content-type", "text/html;charset=UTF-8");
            ChannelFuture channelFuture = ctx.writeAndFlush(response);
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        } else {
            RandomAccessFile file = null;
            ChunkedFile chunkedFile = null;
            try {
                file = new RandomAccessFile(filePath, "r");
                // big content download, avoid outOfMemoryError
                chunkedFile = new ChunkedFile(file, 0, file.length(), 8192);
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
