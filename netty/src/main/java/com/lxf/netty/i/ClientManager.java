package com.lxf.netty.i;

import io.netty.channel.EventLoop;

/**
 * 客户端管理
 */
public interface ClientManager<T extends Message> {
    /**
     * 连接服务器
     */
    void connect();

    /**
     * 断开连接
     */
    void disConnect();

    /**
     * 重连
     */
    void reConnect(EventLoop eventLoop);

    /**
     * 接收到消息
     *
     * @param msg 消息
     */
    void receiveMessage(T msg);

    /**
     * 发送消息
     * @param message 消息
     */
    void sendMessage(T message);
}
