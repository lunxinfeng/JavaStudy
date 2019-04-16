package com.lxf.netty;

import com.lxf.netty.callback.ConnectListener;
import com.lxf.netty.callback.MessageCallBack;
import com.lxf.netty.i.ClientManager;
import com.lxf.netty.interpolator.Interpolator;

interface ClientBuilder<B extends ClientBuilder,R extends ClientManager> {
    /**
     * 连接监听
     */
    B connectListener(ConnectListener connectListener);

    /**
     * 消息监听
     */
    B messageListener(MessageCallBack messageCallBack);

    /**
     * 最大重连次数
     */
    B reConnectNumMax(int num);

    /**
     * 插值器，重连的频率
     */
    B reConnectInterval(Interpolator interpolator);

    /**
     * 生成客户端
     */
    R build();
}
