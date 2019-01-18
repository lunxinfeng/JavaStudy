package netty.socket.demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class ClientMain {
    private final String host;
    private final int port;

    public ClientMain(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 12345;
//        String host = "app.izis.cn";
//        int port = 8096;
        new ClientMain(host,port).start();
    }

    private void start() {
        ClientChannelHandler clientChannelHandler = new ClientChannelHandler();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class) //指定所使用的NIO传输Channel
                .remoteAddress(new InetSocketAddress(host,port)) //使用指定的端口设置套接字地址
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(clientChannelHandler);
                    }
                });

        try {
            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭EventLoopGroup，释放所有的资源
                eventLoopGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
