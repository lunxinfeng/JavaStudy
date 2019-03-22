package com.lxf.netty;

import com.lxf.netty.callback.ConnectListener;
import com.lxf.netty.callback.MessageCallBack;

public class MinaManager {

    /**
     * mina地址
     */
  private static final String MINA_ADDRESS = "47.99.41.182";
    /**
     * 公共端端口
     */
//    public static final int PORT_PUBLIC = 8101;
//    public static final int PORT_PVP = 8102;
    public static final int PORT_PUBLIC = 8901;
    public static final int PORT_PVP = 8902;
    //公共端
    private static Client client_public;
    //对战端
    private static Client client_pvp;

//    public static ConnectListener connectListenerPublic;
//    public static ConnectListener connectListenerPvp;
//
//    public static MessageCallBack messageCallBackPublic;
//    public static MessageCallBack messageCallBackPvp;


    public static void connectServer(int port,ConnectListener connectListener,MessageCallBack messageCallBack) {
        switch (port) {
            case PORT_PUBLIC:
                connectPublicServer(connectListener,messageCallBack);
                break;
            case PORT_PVP:
                connectPvpServer(connectListener,messageCallBack);
                break;
        }
    }

    private static void connectPublicServer(ConnectListener connectListener,MessageCallBack messageCallBack) {
        if (client_public == null){
            client_public = new Client(MINA_ADDRESS, PORT_PUBLIC);
            client_public.setConnectListener(connectListener);
            client_public.setMessageCallBack(messageCallBack);
        }
        client_public.connectServer();
    }

    private static void connectPvpServer(ConnectListener connectListener,MessageCallBack messageCallBack) {
        if (client_pvp == null){
            client_pvp = new Client(MINA_ADDRESS, PORT_PVP);
            client_pvp.setConnectListener(connectListener);
            client_pvp.setMessageCallBack(messageCallBack);
        }
        client_pvp.connectServer();
    }

    public static void disConnect(int port) {
        switch (port) {
            case PORT_PUBLIC:
                client_public.disConnect();
                break;
            case PORT_PVP:
                client_pvp.disConnect();
                break;
        }
    }

    public static void minaSend(int method, int gameId, int otherUser, String msg, int port) {
            switch (port) {
                case PORT_PUBLIC:
                    client_public.sendMessage(method,gameId,otherUser,msg);
                    break;
                case PORT_PVP:
                    client_pvp.sendMessage(method,gameId,otherUser,msg);
                    break;
            }
    }
}
