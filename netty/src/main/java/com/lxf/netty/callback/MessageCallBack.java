package com.lxf.netty.callback;

import com.lxf.netty.message.MsgPack;

public interface MessageCallBack {
    /**
     * 接收到服务器返回的消息
     */
    void onReceiveMessage(MsgPack msgPack);
}
