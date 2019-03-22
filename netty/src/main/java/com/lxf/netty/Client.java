package com.lxf.netty;

import com.lxf.netty.callback.ConnectListener;
import com.lxf.netty.callback.MessageCallBack;
import com.lxf.netty.codec.MessageDecoder;
import com.lxf.netty.codec.MessageEncoder;
import com.lxf.netty.handler.ClientHandler;
import com.lxf.netty.log.Log;
import com.lxf.netty.message.MessageGenerator;
import com.lxf.netty.message.MsgPack;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;


public class Client implements ChannelFutureListener {

    private static final int reConnectNum = 5;
    private int reConnectIndex;
    public Channel channel;//通道
    private Bootstrap bootstrap;//启动器
    private String host;
    private int port;
    private ConnectListener connectListener;
    private MessageCallBack messageCallBack;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.reConnectIndex = 0;

        bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,8000)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast("decoder", new MessageDecoder())
                                .addLast("encoder", new MessageEncoder())
                                .addLast("heart", new IdleStateHandler(60, 15, 15))
                                .addLast(new ClientHandler(Client.this));
                    }
                });
    }

    public void setConnectListener(ConnectListener connectListener) {
        this.connectListener = connectListener;
    }

    public void setMessageCallBack(MessageCallBack messageCallBack) {
        this.messageCallBack = messageCallBack;
    }

    public void connectServer() {
        try {
            disConnect();

            bootstrap.connect(host, port)
                    .sync()
                    .addListener(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void disConnect() {
        if (channel != null) {
            channel.close();
            channel.disconnect();
        }
    }

    public void sendMessage(MsgPack msgPack) {
        if (channel != null && channel.isWritable()) {
            channel.writeAndFlush(msgPack);
        }
    }

    public void sendMessage(int method, int groupId, int toId, String msg) {
        sendMessage(MessageGenerator.message(method, groupId, toId, msg));
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            Log.warn("连接服务器成功：" + port);
            channel = future.channel();
            reConnectIndex = 0;
            if (connectListener != null)
                connectListener.onConnectSuccess(this);
        } else {
            reConnect(future.channel().eventLoop());
        }
    }

    public void reConnect(EventLoop eventLoop) {
        if (reConnectIndex <= reConnectNum) {
            //重连
            Log.warn("连接服务器失败：" + port);
            reConnectIndex++;
            eventLoop
                    .schedule(new Runnable() {
                        @Override
                        public void run() {
                            Log.warn("开始重连：" + port);
                            connectServer();
                        }
                    }, 1000 * reConnectIndex * reConnectIndex, TimeUnit.MILLISECONDS);
        } else {
            Log.warn("连接服务器失败，重连后依然失败：" + port);
            if (connectListener != null)
                connectListener.onConnectFail();
        }
    }

    public void receiveMessage(MsgPack msgPack){
        if (messageCallBack!=null)
            messageCallBack.onReceiveMessage(msgPack);
    }

}
