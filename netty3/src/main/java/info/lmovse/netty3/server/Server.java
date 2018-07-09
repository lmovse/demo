package info.lmovse.netty3.server;

import info.lmovse.netty3.server.handler.TimePoJoEncodeHandler;
import info.lmovse.netty3.server.handler.TimeServerPoJoHandler;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;

import static java.util.concurrent.Executors.newCachedThreadPool;

public class Server {

    public static void main(String[] args) {
        // make server-side factory to manage channels and related resources
        NioServerSocketChannelFactory factory =
                new NioServerSocketChannelFactory(newCachedThreadPool(), newCachedThreadPool());
        // make bootstrap helper to setup server
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        bootstrap.setPipelineFactory(() -> Channels.pipeline(new TimeServerPoJoHandler(), new TimePoJoEncodeHandler()));
        // set protocol options
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        bootstrap.bind(new InetSocketAddress(7070));
    }
}
