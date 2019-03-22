package com.lxf.netty.handler;

import com.lxf.netty.Client;
import com.lxf.netty.message.MessageGenerator;
import com.lxf.netty.message.MsgPack;
import com.lxf.netty.log.Log;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;


public class ClientHandler extends SimpleChannelInboundHandler<MsgPack> {
    private Client client;

    public ClientHandler(Client client) {
        this.client = client;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        Log.info("channelRegistered");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        Log.warn("channelUnregistered");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Log.info("channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        Log.warn("channelInactive");
        client.reConnect(ctx.channel().eventLoop());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgPack msg) {
        Log.info("接收到信息：" + msg.toString());
        client.receiveMessage(msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent){
            switch (((IdleStateEvent) evt).state()){
                case READER_IDLE:
                    Log.warn("读空闲");
                    //重连
                    client.connectServer();
                    break;
                case WRITER_IDLE:
                    Log.warn("写空闲");
                    //心跳
                    ctx.writeAndFlush(MessageGenerator.heart());
                    break;
                case ALL_IDLE:
                    Log.warn("读写空闲");
                    break;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Log.error("exceptionCaught：" + cause.getMessage());
        ctx.close();
    }
}
