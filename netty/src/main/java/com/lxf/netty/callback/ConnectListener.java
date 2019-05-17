package com.lxf.netty.callback;

import com.lxf.netty.Client;

public interface ConnectListener {
    /**
     * 连接成功
     */
    void onConnectSuccess(Client client);

    /**
     * 连接断开，正在重连
     */
    void onReConnect();

    /**
     * 连接失败，重连后依然失败
     */
    void onConnectFail();
}