package info.lmovse.netty4.protocol.client;

import info.lmovse.netty4.protocol.client.handler.AuthRequestHandler;
import info.lmovse.netty4.protocol.client.handler.ClientHandler;
import info.lmovse.netty4.protocol.client.handler.HeartbeatRequestHandler;
import info.lmovse.netty4.protocol.codec.ExchangeDecoder;
import info.lmovse.netty4.protocol.codec.ExchangeEncoder;
import info.lmovse.netty4.protocol.codec.ProtocolCodec;
import info.lmovse.netty4.protocol.codec.TransportDecoder;
import info.lmovse.netty4.protocol.codec.TransportEncoder;
import info.lmovse.netty4.protocol.serialization.KryoSerialization;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProtocolClient {

    // ensure there can be only one worker in client side
    private static final NioEventLoopGroup WORKER = new NioEventLoopGroup();
    private final Bootstrap bootstrap = new Bootstrap();
    private final ProtocolCodec codec = new ProtocolCodec(new KryoSerialization());
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        new ProtocolClient().connect(new InetSocketAddress(7070));
    }

    private void connect(final SocketAddress address) {
        try {
            // basic config
            bootstrap.group(WORKER)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);

            // config handlers
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(final SocketChannel ch) {
                    ch.pipeline()
                            .addLast("transport-decoder", new TransportDecoder(codec))
                            .addLast("exchange-decoder", new ExchangeDecoder())
                            .addLast("transport-encoder", new TransportEncoder(codec))
                            .addLast("auth-handler", new AuthRequestHandler())
                            .addLast("heartbeat-handler", new HeartbeatRequestHandler())
                            .addLast("exchange-encoder", new ExchangeEncoder())
                            .addLast("handler", new ClientHandler());
                }
            });

            // connect server
            ChannelFuture channelFuture = bootstrap.connect(address).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
//            can't shutdown nioEventGroup
//            worker.shutdownGracefully();
            executor.schedule(() -> connect(address), 1000, TimeUnit.MILLISECONDS);
        }
    }
}
