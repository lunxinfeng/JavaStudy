package com.lxf.netty;

import com.lxf.netty.callback.ConnectListener;
import com.lxf.netty.callback.MessageCallBack;
import com.lxf.netty.codec.MessageDecoder;
import com.lxf.netty.codec.MessageEncoder;
import com.lxf.netty.handler.ClientHandler;
import com.lxf.netty.i.ClientManager;
import com.lxf.netty.interpolator.Interpolator;
import com.lxf.netty.interpolator.YZInterpolator;
import com.lxf.netty.log.Log;
import com.lxf.netty.message.MsgPack;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 客户端
 */
public class Client implements ChannelFutureListener, ClientManager<MsgPack> {

    //最大重连次数
    private int reConnectNum = 5;
    //当前重连次数
    private int reConnectIndex;
    //通道
    private Channel channel;
    //启动器
    private Bootstrap bootstrap;
    //服务器域名
    private String host;
    //服务器端口
    private int port;
    //连接监听
    private ConnectListener connectListener;
    //消息监听
    private MessageCallBack messageCallBack;
    //插值器，重连频率
    private Interpolator interpolator;
    //是否主动断开连接
    private boolean disConnectByUser = false;

    private Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.reConnectIndex = 0;
        this.interpolator = new YZInterpolator();

        bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 8000)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast("decoder", new MessageDecoder())
                                .addLast("encoder", new MessageEncoder())
                                .addLast("heart", new IdleStateHandler(60, 15, 15))
                                .addLast(new ClientHandler(Client.this));
                    }
                });
    }

    @Override
    public void connect() {
        try {
            ChannelFuture future = bootstrap.connect(host, port)
                    .addListener(this)
                    .sync();//阻塞连接
            future.channel().closeFuture().sync();//断开监听
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (disConnectByUser){
                //释放NIO线程组
                bootstrap.config().group().shutdownGracefully();
            }else{
                if (connectListener != null)
                    connectListener.onReConnect();
                reConnect(bootstrap.config().group().next());
            }
        }
    }

    @Override
    public void disConnect() {
        if (channel != null) {
            disConnectByUser = true;
            channel.close();
            channel.disconnect();
            Log.warn("主动断开连接：" + port);
        }
    }

    @Override
    public void sendMessage(MsgPack msgPack) {
        if (channel != null && channel.isWritable()) {
            channel.writeAndFlush(msgPack);
        }
    }

    @Override
    public void operationComplete(ChannelFuture future) {
        if (future.isSuccess()) {
            Log.warn("连接服务器成功：" + port);
            disConnectByUser = false;
            channel = future.channel();
            reConnectIndex = 0;
            if (connectListener != null)
                connectListener.onConnectSuccess(this);
        } else {
            Log.warn("连接服务器失败：" + port);
//            reConnect(future.channel().eventLoop());
        }
    }

    @Override
    public void reConnect(EventLoop eventLoop) {
        if (reConnectIndex < reConnectNum) {
            //重连
            reConnectIndex++;
            eventLoop
                    .schedule(new Runnable() {
                        @Override
                        public void run() {
                            Log.warn("开始重连：" + reConnectIndex + "\t" + port);
                            connect();
                        }
                    }, interpolator.interpolator(reConnectIndex, reConnectNum), TimeUnit.MILLISECONDS);
        } else {
            Log.warn("连接服务器失败，重连后依然失败：" + port);
            disConnectByUser = true;
            if (connectListener != null)
                connectListener.onConnectFail();
        }
    }

    @Override
    public void receiveMessage(MsgPack msgPack) {
        if (messageCallBack != null)
            messageCallBack.onReceiveMessage(msgPack);
    }


    public static class Builder implements ClientBuilder<Builder, Client> {
        private Client client;

        public Builder(String host, int port) {
            client = new Client(host, port);
        }

        @Override
        public Builder connectListener(ConnectListener connectListener) {
            client.connectListener = connectListener;
            return this;
        }

        @Override
        public Builder messageListener(MessageCallBack messageCallBack) {
            client.messageCallBack = messageCallBack;
            return this;
        }

        @Override
        public Builder reConnectNumMax(int num) {
            client.reConnectNum = num;
            return this;
        }

        @Override
        public Builder reConnectInterval(Interpolator interpolator) {
            client.interpolator = interpolator;
            return this;
        }

        @Override
        public Client build() {
            return client;
        }
    }
}
