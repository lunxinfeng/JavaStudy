package com.lxf.netty.callback;

import com.lxf.netty.Client;

public interface ConnectListener {
    /**
     * 连接成功
     */
    void onConnectSuccess(Client client);

    /**
     * 连接失败，重连后依然失败
     */
    void onConnectFail();
}